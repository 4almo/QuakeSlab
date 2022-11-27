package fr.almo.quakeslab.core;

public class Player {

    protected Player player;
    protected float cooldown;
    protected float dashCooldown;
    protected Board board;
    protected int score;
    protected GunProfile gunProfile;
    protected int jumpPadCooldown;
    protected int killstreak;

    public Player(Player player, GunProfile gunProfile) {
        this.player = player;
        this.cooldown = 0;
        this.dashCooldown = 0;
        this.board = null;
        this.score = 0;
        this.gunProfile = gunProfile;
        this.jumpPadCooldown = 0;
        this.killstreak = 0;

}
