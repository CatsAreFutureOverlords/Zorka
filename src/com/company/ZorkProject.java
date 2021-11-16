package com.company;

import java.io.PrintStream;
import java.util.Scanner;
import static com.company.ZorkProject.so;
import static com.company.ZorkProject.scan;

public class ZorkProject {

    static PrintStream so = System.out;
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        Player player = new Player();
        PathFactory factory = new PathFactory();
        Maze maze = new Maze(factory);
        maze.createPath();
        Game game = new Game(player, maze);

        so.println("Welcome to the Legend of Zorka!");

        while (game.stillPlaying) {
            game.intro();
            String[] instruc = game.inputPlayer();
            game.parsing(instruc);
            game.update();
        }

        if(!player.health) {
            so.println("You died..... Game Over!");
        }

        so.println("Thank you for playing!");

    }
}

class Game {
    boolean stillPlaying = true;
    Maze currGame;
    Player player1;
    public Room current;

    Game(Player player1, Maze currGame) {
        this.player1 = player1;
        this.currGame = currGame;
        current = currGame.room19;
    }

    public void intro() {
        so.printf("\n%s\n", current.getName());
        so.printf("%s\n", current.getDescrip());
        so.printf("Action Points: %d\n", player1.score);
    }

    public String[] inputPlayer() {
        so.print(">> ");
        String raw = scan.nextLine();
        player1.score += 1;
        return raw.split(" ");
    }

    public void parsing(String[] word_list) {
        if (word_list[0].equalsIgnoreCase("Fight") || word_list[0].equalsIgnoreCase("Attack")
                || word_list[0].equalsIgnoreCase("Kill")) { fightmethod(word_list); }
        else if (word_list[0].equalsIgnoreCase("See")) { lookAt(word_list); }
        else if (word_list[0].equalsIgnoreCase("Take")) { take(word_list); }
        else if (word_list[0].equalsIgnoreCase("Drop")) { drop(word_list); }
        else if (word_list[0].equalsIgnoreCase("Put")) { putIn(word_list); }
        else if (word_list[0].equalsIgnoreCase("Inventory")) { invenChk(); }
        else if (word_list[0].equalsIgnoreCase("Help")) { help(); }
        else if (word_list[0].equalsIgnoreCase("Move") || word_list[0].equalsIgnoreCase("Go")
                || word_list[0].equalsIgnoreCase("Walk") || word_list[0].equalsIgnoreCase("Run")) { moving(word_list); }
        else if (word_list[0].equalsIgnoreCase("North") || word_list[0].equalsIgnoreCase("Forward")) { north(); }
        else if (word_list[0].equalsIgnoreCase("South") || word_list[0].equalsIgnoreCase("Backward")) { south(); }
        else if (word_list[0].equalsIgnoreCase("East") || word_list[0].equalsIgnoreCase("Right")) { east(); }
        else if (word_list[0].equalsIgnoreCase("West") || word_list[0].equalsIgnoreCase("Left")) { west(); }
        else if (word_list[0].equalsIgnoreCase("Up")) { up(); }
        else if (word_list[0].equalsIgnoreCase("Down")) { down(); }
        else if (word_list[0].equalsIgnoreCase("Quit")) {
            so.println("The game will end now.");
            stillPlaying = false;
        }
        else { so.println("That's an unknown command. Please try again."); }
    }

    public void fightmethod(String[] word_list) {
        if(word_list.length == 1 ){
            so.println("What are you going to fight?");
        }
        else if(word_list.length == 2 ) {
            so.printf("And just how are you going to fight %s?\n", word_list[1]);
        }
        else if(word_list.length == 3) {
            so.println("With what? You are unarmed!");
        }
        else {
            for (int i = 1; i < word_list.length; i++) {}
        }
    }
    public void lookAt(String[] word_list) {
        for (int i = 1; i < word_list.length; i++) {}
    }
    public void take(String[] word_list) {
        for (int i = 1; i < word_list.length; i++) {}
    }
    public void drop(String[] word_list) {
        for (int i = 1; i < word_list.length; i++) {}
    }
    public void putIn(String[] word_list) {
        if( word_list.length == 1 ) {
            so.println("And put what?");
        }
        else if (word_list.length == 2) {
            so.println("And where?");
        }
        else {
            for (int i = 1; i < word_list.length; i++) {}
        }
    }
    public void invenChk() {}
    public void help() {}
    public void moving(String[] word_list) {
        if(word_list.length == 1) {
            so.println("Do you even know where you're going?");
        }
        else {
            if (word_list[1].equalsIgnoreCase("North") || word_list[1].equalsIgnoreCase("Forward")) { north(); }
            else if (word_list[1].equalsIgnoreCase("South") || word_list[1].equalsIgnoreCase("Backward")) { south(); }
            else if (word_list[1].equalsIgnoreCase("East") || word_list[1].equalsIgnoreCase("Right")) { east(); }
            else if (word_list[1].equalsIgnoreCase("West") || word_list[1].equalsIgnoreCase("Left")) { west(); }
            else if (word_list[1].equalsIgnoreCase("Up")) { up(); }
            else if (word_list[1].equalsIgnoreCase("Down")) { down(); }
            else {
                so.println("Let's face it. You're lost.");
            }
        }
    }

    public void north() {
        if(current.north.isWall()) { current.wall(); }
        else if(!current.north.passable()) { so.println("That's locked!"); }
        else {
            current = current.north.next;
        }
    }
    public void south() {
        if(current.south.isWall()) { current.wall(); }
        else if(!current.south.passable()) { so.println("That's locked!"); }
        else {
            current = current.south.next;
        }
    }
    public void east() {
        if(current.east.isWall()){ current.wall(); }
        else if(!current.east.passable()) { so.println("That's locked!"); }
        else {
            current = current.east.next;
        }
    }
    public void west() {
        if(current.west.isWall()){ current.wall(); }
        else if(!current.west.passable()) { so.println("That's locked!"); }
        else {
            current = current.west.next;
        }
    }
    public void down() {
        if(current.down.isWall()){ so.println("Unless you're a mole, you can't burrow underground."); }
        else if(!current.down.passable()) { so.println("That's locked!"); }
        else {
            current = current.down.next;
        }
    }
    public void up() {
        if(current.up.isWall()){ so.println("If you somehow managed to grow wings, then maybe you could fly."); }
        else if(!current.up.passable()) { so.println("That's locked!"); }
        else {
            current = current.down.next;
        }
    }

    public void update() {
        if(!player1.health) { stillPlaying = false; }
    }
}

class Player {
    final int carrycap = 50;
    public boolean health = true;
    public int score = 1;
    Inventory[] holding;
}

class Maze{
    PathFactory factory;
    Room1 room1;
    Room2 room2;
    Room3 room3;
    Room4 room4;
    Room5 room5;
    Room6 room6;
    Room7 room7;
    Room8 room8;
    Room9 room9;
    Room10 room10;
    Room11 room11;
    Room12 room12;
    Room13 room13;
    Room14 room14;
    Room15 room15;
    Room16 room16;
    Room17 room17;
    Room18 room18;
    Room19 room19;
    Room20 room20;
    BossRoom bossRoom;

    Maze(PathFactory factory) {
        this.factory = factory;
        room1 = new Room1(factory);
        room2 = new Room2(factory);
        room3 = new Room3(factory);
        room4 = new Room4(factory);
        room5 = new Room5(factory);
        room6 = new Room6(factory);
        room7 = new Room7(factory);
        room8 = new Room8(factory);
        room9 = new Room9(factory);
        room10 = new Room10(factory);
        room11 = new Room11(factory);
        room12 = new Room12(factory);
        room13 = new Room13(factory);
        room14 = new Room14(factory);
        room15 = new Room15(factory);
        room16 = new Room16(factory);
        room17 = new Room17(factory);
        room18 = new Room18(factory);
        room19 = new Room19(factory);
        room20 = new Room20(factory);
        bossRoom = new BossRoom(factory);
    }

    public void createPath() {
        room1.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room2.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room3.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room4.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room5.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room6.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room7.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room8.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room9.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room10.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room11.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room12.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room13.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room14.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room15.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room16.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room17.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room18.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room19.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        room20.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
        bossRoom.pathway(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
                room11, room12, room13, room14, room15, room16, room17, room18, room19, room20, bossRoom);
    }

}

class Side {
    boolean traverse;
    boolean wall;
    Room next;

    Side(){ wall = true; }

    public boolean isWall() { return wall; }
    public boolean passable() { return traverse; }
}

class Door extends Side {
    Door(Room next) {
        this.next = next;
        wall = false;
        traverse = true;
    }
}

class Locked extends Door {
    boolean closed;

    Locked(Room next) {
        super(next);
        traverse = false;
        closed = true;
    }

    public void condition() {
        closed = false;
        traverse = true;
    }
}

class PathFactory {
    public Side createWall() { return new Side(); }
    public Door createDoor(Room room) { return new Door(room); }
    public Locked createLock(Room room) { return new Locked(room); }

    public Kiise spawnKiise() { return new Kiise(); }
    public Octostone spawnOcto() { return new Octostone(); }
    public Qanon spawnQanon() { return new Qanon(); }

    public SilverKey silverKey() { return new SilverKey(); }
    public DungeonKey dungeonKey() { return new DungeonKey(); }

    public WoodBlade woodBlade() { return new WoodBlade(); }
    public MisterSword misterSword() { return new MisterSword(); }
    public FireRod fireRod() { return new FireRod(); }
    public Bombs bombs() { return new Bombs(); }
    public WoodShield woodShield() { return new WoodShield(); }
    public HighShield highShield() { return new HighShield(); }
}

// ---------------------------------------------------------------------------------------------------------------------------

abstract class Inventory {
    public String name;
    public String desc;
}

abstract class Weaponry extends Inventory {
    public int weight;
}

abstract class Enemies {
    public boolean alive = true;

    abstract void attack();
    public void killed() { alive = false; }
}

abstract class Obstacle {
    public String name;
    public String descrp;
}

abstract class Room {
    public String name;
    public String descrip;

    public Side north;
    public Side south;
    public Side east;
    public Side west;
    public Side up;
    public Side down;

    Inventory[] onFloor;
    public int itemNum = 0;
    PathFactory direction;

    Room(PathFactory direction) {
        this.direction = direction;
        north = direction.createWall();
        south = direction.createWall();
        east = direction.createWall();
        west = direction.createWall();
        up = direction.createWall();
        down = direction.createWall();
    }

    public String getName() {return name;}
    public String getDescrip() {return descrip;}

    abstract void wall();
    abstract void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                          Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                          Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss);
}

// -----------------------------------------------------------------------------------------------------------------

class SilverKey extends Inventory {
    SilverKey() {
        name = "Silver Key";
        desc = "A key made of pure silver. It should open a door somewhere.";
    }
}

class DungeonKey extends Inventory{
    DungeonKey() {
        name = "Dungeon Key";
        desc = "A rather ominous looking key. It probably fits a special looking door.";
    }
}

// --------------------------------------------------------------------------------------------------------------------------------

class WoodBlade extends Weaponry {
    WoodBlade() {
        name = "Wooden Blade";
        desc = "A simple lightweight weapon.";
        weight = 10;
    }
}

class MisterSword extends Weaponry {
    MisterSword() {
        name = "Mister Sword";
        weight = 25;
    }
}

class FireRod extends Weaponry {
    FireRod() {
        name = "Flame Rod";
        desc = "Allows you to summon a flame to light your way.";
        weight = 15;
    }
}

class Bombs extends Weaponry {
    Bombs() {
        name = "Bombs";
        desc = "These explosive bombs can really pack a punch.";
        weight = 20;
    }
}

class WoodShield extends Weaponry {
    WoodShield () {
        name = "Wooden Buckler";
        weight = 15;
    }
}

class HighShield extends Weaponry {
    HighShield() {
        name = "Highlian Shield";
        weight = 25;
    }
}

// ---------------------------------------------------------------------------------------------------------------------

class Kiise extends Enemies {
    public void attack() { so.println("The Kiise swoops in for the kill."); }
}

class Octostone extends Enemies {
    public void attack() { so.println("The Octostone shoots a fatal projectile."); }
}

class Qanon extends Enemies{
    public void attack() { so.println("Qanon unleashes his ultimate magick!"); }
}

// ------------------------------------------------------------------------------------------------------------------------

class Room1 extends Room {
    Room1(PathFactory direction) {
        super(direction);
        name = "The West Wing";
        descrip = "The room is ornately decorated. A large Portrait hangs on the wall.";
    }

    public void wall() { so.println("There is no way through the thick stone wall."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room2);
    }
}

class Room2 extends Room {
    Room2(PathFactory direction) {
        super(direction);
        name = "Armoury";
        descrip = "A long abandoned weaponry storage room. The weapons are rusted from disuse.";
        itemNum = 1;
    }

    public void wall() { so.println("It's impossible to phase through the castle walls."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room3);
        south = direction.createDoor(room1);
        east = direction.createDoor(room7);
    }
}

class Room3 extends Room {
    Room3(PathFactory direction) {
        super(direction);
        name = "Narrow Path";
        descrip = "You’re in a tight passageway that goes North and South. It is a rather tight squeeze.";
    }

    public void wall() { so.println("There isn't even enough room to turn in any direction."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room6);
        south = direction.createDoor(room2);
    }
}

class Room4 extends Room {
    Room4(PathFactory direction) {
        super(direction);
        name = "Balcony";
        descrip = "You’re standing on a high balcony where falling off the edges ensures certain death. There is a constant breeze at all times.";
    }

    public void wall() { so.println("You will fall to your death off this balcony!"); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        east = direction.createDoor(room5);
    }
}

class Room5 extends Room {
    Room5(PathFactory direction) {
        super(direction);
        name = "Corridor";
        descrip = "You’re in a corridor that goes East and West.";
        itemNum = 1;
    }

    public void wall() { so.println("You're not a ghost; you can't phase through walls."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        east = direction.createDoor(room11);
        west = direction.createDoor(room4);
    }
}


class Room6 extends Room {
    Room6(PathFactory direction) {
        super(direction);
        name = "Hallway";
        descrip = "There are paths open in every direction: North, East, South, West.";
    }

    public void wall() { so.println("You can't go this way."); }

    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room3);
        south = direction.createDoor(room7);
        east = direction.createDoor(room20);
        west = direction.createDoor(room7);
    }
}

class Room7 extends Room {
    Room7(PathFactory direction) {
        super(direction);
        name = "Fountain Room";
        descrip = "A water Fountain sits at the centre of the room.";
    }

    public void wall() { so.println("That's a steep waterfall!"); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room6);
        east = direction.createDoor(room6);
        west = direction.createDoor(room2);
    }
}

class Room8 extends Room {
    Room8(PathFactory direction) {
        super(direction);
        name = "Store Room";
        descrip = "Back when there were still people, this would have been where they stored food and ingredients.";
    }

    public void wall() { so.println("The boulders will collapse."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room10);
        south = direction.createDoor(room9);
    }
}

class Room9 extends Room {
    Room9(PathFactory direction) {
        super(direction);
        name = "Kitchen";
        descrip = "The stoves have long since been abandoned.";
    }

    public void wall() { so.println("You can't go through the greasy walls."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room10);
        west = direction.createDoor(room8);
    }
}

class Room10 extends Room {
    Room10(PathFactory direction) {
        super(direction);
        name = "Hallway";
        descrip = "There are paths open in every direction: North, East, South, West.";
    }

    public void wall() { so.println("You can't go this way."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room20);
        south = direction.createDoor(room9);
        east = direction.createDoor(room18);
        west = direction.createDoor(room8);
    }
}

class Room11 extends Room {
    Kiise kiise;

    Room11(PathFactory direction) {
        super(direction);
        name = "Dining Room";
        descrip = "The giant table would have truly held a feast for a king.";
        kiise = direction.spawnKiise();
    }

    public void wall() {so.println("You can't go this way.");}
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room12);
        south = direction.createDoor(room20);
        east = direction.createDoor(room15);
        west = direction.createDoor(room5);
    }
}

class Room12 extends Room {
    Room12(PathFactory direction) {
        super(direction);
        name = "Room of the Hero";
        descrip = "Something about the room just calls out to you.";
    }

    public void wall() { so.println("You can't go through the thick walls."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        south = direction.createDoor(room11);
        east = direction.createDoor(room13);
    }
}

class Room13 extends Room {
    Room13(PathFactory direction) {
        super(direction);
        name = "Shrine of the Goddess";
        descrip = "You’re standing in what looks like a shrine. You can feel an ethereal power emanating from every corner of the room.";
    }

    public void wall() { so.println("A dense forest blocks your way."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        west = direction.createDoor(room12);
    }
}

class Room14 extends Room {
    Room14(PathFactory direction) {
        super(direction);
        name = "Smithy";
        descrip = "The forge has long since gone cold.";
    }

    public void wall() { so.println("The iron building bars your way."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        south = direction.createDoor(room15);
        west = direction.createDoor(room15);
    }
}

class Room15 extends Room {
    Octostone octo;

    Room15(PathFactory direction) {
        super(direction);
        name = "Training Grounds";
        descrip = "A dirt arena where soldiers once tested their mettle.";
        octo = direction.spawnOcto();
    }

    public void wall() {so.println("You can't go this way.");}
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room14);
        south = direction.createDoor(room17);
        east = direction.createDoor(room14);
        west = direction.createDoor(room11);
    }
}

class Room16 extends Room {
    Room16(PathFactory direction) {
        super(direction);
        name = "Hearth";
        descrip = "A golden Brazier warms the entire room.";
    }

    public void wall() { so.println("You can't touch the flaming hot stones."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        west = direction.createLock(room17);
    }
}

class Room17 extends Room {
    Room17(PathFactory direction) {
        super(direction);
        name = "Display Case";
        descrip = "Priceless relics line the room.";
    }

    public void wall() {so.println("You can't go this way.");}
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room15);
        south = direction.createDoor(room18);
        east = direction.createLock(room16);
        west = direction.createDoor(room20);
    }
}

class Room18 extends Room {
    Room18(PathFactory direction) {
        super(direction);
        name = "Underground Passage";
        descrip = "At the centre of the room, an Old Man in robes just stands there, menacingly.";
    }

    public void wall() { so.println("The cave blocks your way."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room17);
        east = direction.createDoor(room19);
        west = direction.createDoor(room10);
    }
}

class Room19 extends Room {
    Room19(PathFactory direction) {
        super(direction);
        name = "Dead End Cave";
        descrip = "You’re standing in a dark cave with walls blocking every direction. The only way out is to the North.";
    }

    public void wall() { so.println("The dark cave completely surrounds you."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room18);
    }
}

class Room20 extends Room {
    Room20(PathFactory direction) {
        super(direction);
        name = "Dungeon Entrance";
        descrip = "There is a giant Entrance in the floor with an ornate lock at the centre, only a special key can open it.\n" +
                "There are doors in every direction: North, East, South, West.";
    }

    public void wall() { so.println("You can't go this way."); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        north = direction.createDoor(room11);
        south = direction.createDoor(room10);
        east = direction.createDoor(room17);
        west = direction.createDoor(room6);
        down = direction.createLock(boss);
    }
}

class BossRoom extends Room {
    Qanon qanon;

    BossRoom(PathFactory direction) {
        super(direction);
        name = "Boss Lair";
        descrip = "Qanon waits for you.";
        qanon = direction.spawnQanon();
    }

    public void wall() { so.println("YOU SHALL NOT PASS!!!"); }
    public void pathway(Room1 room1, Room2 room2, Room3 room3, Room4 room4, Room5 room5, Room6 room6, Room7 room7,
                        Room8 room8, Room9 room9, Room10 room10, Room11 room11, Room12 room12, Room13 room13, Room14 room14,
                        Room15 room15, Room16 room16, Room17 room17, Room18 room18, Room19 room19, Room20 room20, BossRoom boss) {
        up = direction.createLock(room20);
    }
}