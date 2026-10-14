package com.game.front.bridge;

import com.game.front.sprite.PlayerSprites;
import com.game.front.sprite.PlayerState;
import com.game.front.view.BallView;
import com.game.front.view.Camera;
import com.game.front.view.DebugView;
import com.game.front.view.FieldView;
import com.game.front.view.GoalView;
import com.game.front.view.HudInfo;
import com.game.front.view.HudView;
import com.game.front.view.PlayerView;
import com.game.front.view.ViewConfig;
import com.game.front.world.MatchWorld;
import com.game.front.world.WorldFactory;
import com.game.front.world.WorldLayout;
import com.game.models.Ball;
import com.game.models.Joueur;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Hitbox;

import javafx.scene.canvas.GraphicsContext;

public class MatchPresenter {
    private static final double SUPER_SHOT_POWER = 100;
    private static final double GOAL_CELEBRATION_SECONDS = 3;

    private final ControleurHumain controller;
    private final Camera camera = new Camera(ViewConfig.WIDTH, ViewConfig.HEIGHT,
            WorldLayout.MAP_WIDTH, WorldLayout.MAP_HEIGHT);
    private final FieldView fieldView = new FieldView();
    private final GoalView goalView = new GoalView();
    private final BallView ballView = new BallView();
    private final DebugView debugView = new DebugView();
    private final HudView hudView = new HudView();
    private final PlayerView playerView = new PlayerView(PlayerSprites.KIT_BLUE);
    private final CountdownTimer goalTimer = new CountdownTimer();

    private MatchWorld world;
    private KickDetector kickDetector;
    private GoalWatcher goalWatcher;
    private boolean debug;

    public MatchPresenter(ControleurHumain controller) {
        this.controller = controller;
        buildWorld();
    }

    public void reset() {
        buildWorld();
    }

    private void buildWorld() {
        world = WorldFactory.create(controller, PlayerSprites.sheet());
        kickDetector = new KickDetector();
        goalWatcher = new GoalWatcher();
        goalTimer.cancel();
        playerView.stopAction();
    }

    public void update(double dt) {
        world.update(dt);
        playKickAnimation();
        watchGoal(dt);
        updatePlayerView(dt);
        followPlayer();
    }

    private void playKickAnimation() {
        if (kickDetector.kickHappened(world.getBall())) playerView.play(kickState());
    }

    private PlayerState kickState() {
        if (controller.getActionJoueur() == Ball.Etat.PASSE) return PlayerState.PASSING;
        boolean strong = world.getPlayer().getPuissanceTir() >= SUPER_SHOT_POWER;
        return strong ? PlayerState.SUPER_SHOOTING : PlayerState.SHOOTING;
    }

    private void watchGoal(double dt) {
        if (goalWatcher.scoredNow(world.scoringGoal())) startGoalCelebration();
        if (goalTimer.tick(dt)) playerView.stopAction();
    }

    private void startGoalCelebration() {
        goalTimer.start(GOAL_CELEBRATION_SECONDS);
        playerView.play(PlayerState.CELEBRATING);
    }

    private void updatePlayerView(double dt) {
        Joueur player = world.getPlayer();
        playerView.face(player.getDernierOrientation());
        playerView.update(dt, MovementResolver.resolve(player));
    }

    private void followPlayer() {
        Hitbox hitbox = world.getPlayer().getHitbox();
        camera.follow(hitbox.getCentre().getX(), hitbox.getCentre().getY());
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, ViewConfig.WIDTH, ViewConfig.HEIGHT);
        fieldView.render(gc, camera);
        goalView.render(gc, camera, world.getLeftGoal());
        goalView.render(gc, camera, world.getRightGoal());
        ballView.render(gc, camera, world.getBall().getHitbox());
        playerView.render(gc, camera, world.getPlayer().getHitbox());
        if (debug) debugView.render(gc, camera, world);
        hudView.render(gc, hudInfo());
    }

    private HudInfo hudInfo() {
        return new HudInfo(playerView.getState().name(), playerView.getKit(),
                world.getBall().getEtat().name(), world.getPlayer().getVitesse().norm(), goalTimer.isRunning());
    }

    public void toggleDebug() {
        debug = !debug;
    }

    public void toggleCelebration() {
        if (playerView.isCelebrating()) playerView.stopAction();
        else playerView.play(PlayerState.CELEBRATING);
    }

    public void playTackle() {
        playerView.play(PlayerState.TACKLING);
    }

    public void setKit(int kit) {
        playerView.setKit(kit);
    }
}
