package com.company;
/**
 * Name : Prasham Narayan
 * ROll NO: 2018350
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class user {
    private final String username;
    private final int herotype;//1 for warrior, 2 for theif, 3 for Mage, 4 for Healer

    user(String username, int herotype) {
        this.username = username;
        this.herotype = herotype;
    }

    public String getUsername() {
        return username;
    }

    public int getHerotype() {
        return herotype;
    }
}
interface Comparator<sidekick>{
    public int compare(sidekick first,sidekick second);
}
abstract class hero implements Comparator<sidekick>{
    private final String username;
    protected float hp;
    protected float xp;
    protected int level;
    protected boolean defense_status;
    protected int defense_value;
    private boolean sp_status;
    protected int count_sp_status;
    protected LinkedList<sidekick> my_sidekicks = new LinkedList<>();
    hero(String username) {
        this.username = username;
        hp = 100;
        xp = 0;
        level =1;
        sp_status = true;
        count_sp_status = 0;
    }
    public void setHp(float hp) {
        this.hp = hp;
    }
    public boolean getSp_status() {
        return sp_status;
    }

    public void setXp(float xp) {
        this.xp = xp;
    }
    public String getUsername() {
        return username;
    }
    public int getLevel() {
        return level;
    }
    public float getHp() {
        return hp;
    }
    public float getXp() {
        return xp;
    }

    public int total_sidekick(){
        return my_sidekicks.size();
    }
    public void addsidekick(int type, int sidekick_xp){
        xp -= sidekick_xp;
        if(type == 1){
            sidekick temp = new minions(sidekick_xp);
            my_sidekicks.add(temp);
        }
        if(type == 2){
            sidekick temp = new knight(sidekick_xp);
            my_sidekicks.add(temp);
        }
        this.levelup();
    }
    public void placeback_sidekick(sidekick s){
        my_sidekicks.add(s);
    }
    public sidekick getsidekick(){
        Collections.sort(my_sidekicks, new java.util.Comparator<sidekick>() {
            @Override
            public int compare(sidekick o1, sidekick o2) {
                return o1.compareTo(o2);
            }
        });
        return my_sidekicks.get(0);
    }
    public void printlist(){

        for(int i = 0;i<my_sidekicks.size();i++){
            System.out.println("sidekick type "+my_sidekicks.get(i).getType());
        }
    }
    public sidekick usesidekick(){
        Collections.sort(my_sidekicks, new java.util.Comparator<sidekick>() {
            @Override
            public int compare(sidekick o1, sidekick o2) {
                return o1.compareTo(o2);
            }
        });
        sidekick temp = my_sidekicks.pollFirst();
        return temp;
    }
    public void levelup(){
        if(xp>=20){
            level = 2;
            hp = 150;
        }
        else if(xp>=60){
            level = 3;
            hp = 200;
        }
        else if(xp>=120){
            level = 4;
            hp = 250;
        }
        else{
            level = 1;
            hp = 100;
        }
    }
    public void checkhp(){
        if(level == 1 && hp>100) {
            hp = 100;
        }
        if(level == 2 && hp>150){
            hp = 150;
        }
        if(level == 3 && hp>200){
            hp = 200;
        }
        if(level == 4 && hp>250){
            hp = 250;
        }
    }

    public void afterfight(boolean win, monster m){
        if(win == true){
            increaseXp(m.getLevel());
        }
        //restore health
        if(level == 1){
            hp = 100;
        }
        else if(level == 2){
            hp = 150;
        }
        else if(level == 3){
            hp = 200;
        }
        else{
            hp = 250;
        }
    }

    public void increaseXp(int level){
        xp+= level*20;
        this.levelup();
    }
    public void printdetails(){
        System.out.println();
    }
    public void takeattack(float attack_val){
        if(defense_status == true){
            if(defense_value<attack_val)
                hp -=  (attack_val - defense_value);
        }
        else {
            hp -= attack_val;
        }
    }
    abstract void move(int choice,monster m);
}
class warrior extends hero{
    private int count = 0;
    private boolean sp_activated;

    warrior(String username) {
        super(username);
        sp_activated = false;
        defense_status = false;
        defense_value = 3;
    }
    public void setDefense_status(boolean defense_status) {
        this.defense_status = defense_status;
    }
    public int getDefense_value() {
        return defense_value;
    }
    public void attack(monster m){
        if(sp_activated==true && count>0){
            count--;
            if(count == 0){
                sp_activated = false;
            }
            System.out.println("You inflicted 15 damage") ;
            m.takeattack(15);
        }
        else{
            System.out.println("You inflicted 10 damage");
            m.takeattack(10);
        }
    }


    public void defense(){
        if(sp_activated == true && count>0){
            count--;
            if(count == 0){
                sp_activated = false;
                defense_value = 3;
            }
            defense_value = 8;
        }
        defense_status = true;
    }
    public void specialpower(){
        sp_activated = true;
        count = 3;
    }
    @Override
    public void move(int choice,monster m){
        if(defense_status == true){
            defense_status = false;
        }
        if(choice == 1){
            this.attack(m);
        }
        else if(choice == 2){
            this.defense();
        }
        else{
            this.specialpower();
        }
    }

    @Override
    public int compare(sidekick first, sidekick second) {
        return first.compareTo(second);
    }
}
class mage extends hero{
    private int count = 0;
    private boolean sp_activated;
    mage(String username) {
        super(username);
        defense_status = false;
        defense_value = 5;
    }
    public void setDefense_status(boolean defense_status) {
        this.defense_status = defense_status;
    }
    public int getDefense_value() {
        return defense_value;
    }
    public void attack(monster m){
        System.out.println("You inflicted 5 damage");
        m.takeattack(5);
    }

    public void defense(){
        defense_status = true;
    }
    public void special_power(){
        sp_activated = true;
        count = 0;
    }
    @Override
    public void move(int choice,monster m){
        if(defense_status == true){
            defense_status = false;
        }
        if(count<3 && sp_activated == true){
            float mhp = m.getHp();
            m.takeattack((mhp*5)/100);
            count++;
        }
        else{
            sp_activated = false;
        }
        if(choice == 1){
            this.attack(m);
        }
        else if(choice == 2){
            this.defense();
        }
        else{
            this.special_power();
        }
    }
    @Override
    public int compare(sidekick first, sidekick second) {
        return first.compareTo(second);
    }
}

class healer extends hero{
    private boolean sp_activated;
    private int count;
    healer(String username) {
        super(username);
        defense_status = false;
        defense_value = 8;
        count = 0;
        sp_activated = false;
    }
    public void setDefense_status(boolean defense_status) {
        this.defense_status = defense_status;
    }
    public int getDefense_value() {
        return defense_value;
    }

    public void attack(monster m){
        System.out.println("You inflicted 5 damage");
        m.takeattack(5);
    }
    public void defense(){
        defense_status = true;
    }
    public void special_power(){
        sp_activated = true;
        count = 0;
    }
    @Override
    public void move(int choice,monster m){
        if(defense_status == true){
            defense_status = false;
        }
        if(sp_activated == true && count<3){
            this.increasehp();
            count++;
        }
        else{
            sp_activated = false;
        }
        if(choice == 1){
            this.attack(m);
        }
        else if(choice == 2){
            this.defense();
        }
        else{
            this.special_power();
        }
    }
    public void increasehp(){
        hp+=((hp*5)/100);
        checkhp();
    }
    @Override
    public int compare(sidekick first, sidekick second) {
        return first.compareTo(second);
    }
}

class theif extends hero{

    theif(String username) {
        super(username);
        defense_status = false;
        defense_value = 4;
    }
    public void setDefense_status(boolean defense_status) {
        this.defense_status = defense_status;
    }
    public int getDefense_value() {
        return defense_value;
    }
    public void attack(monster m){
        System.out.println("You inflicted 6 damage");
        m.takeattack(6);
    }
    public void defense(){
        defense_status = true;
    }
    public void special_power(monster m){
        float mhp = m.getHp();
        mhp = (mhp*30)/100;
        hp+=mhp;
        checkhp();
        System.out.println("Stolen "+mhp+" HP from the monster");
        m.takeattack(mhp);
    }

    @Override
    public void move(int choice,monster m){
        if(defense_status == true){
            defense_status = false;
        }
        if(choice == 1){
            this.attack(m);
        }
        else if(choice == 2){
            this.defense();
        }
        else{
            this.special_power(m);
        }
    }
    @Override
    public int compare(sidekick first, sidekick second) {
        return first.compareTo(second);
    }
}

abstract class sidekick{
    private float hp;
    protected float xp;
    protected float damage;
    private float xpincrease;
    protected int type;

    sidekick(){
        hp = 100;
        xpincrease = 0;
    }
    public int getType() {
        return type;
    }
    public int compareTo(sidekick x){
        if(x.xp>this.xp) return 1;
        else if(x.xp == this.xp) return 0;
        else return -1;
    }
    public float getHp() {
        return hp;
    }
    public void printHp(){
        System.out.println("Sidekick's HP: "+this.hp+"/100");
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getXp() {
        return xp;
    }

    public void setXp(float xp) {
        this.xp = xp;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void afterfight(int n){
        //n = xp gained by hero
        n = n*2;
        hp = 100;
        xp+=n;
        xpincrease += n/10;
        if(xpincrease>=5){
            xp -= 5;
            damage+=1;
        }

    }

    abstract  public ArrayList<sidekick> makeclones() throws CloneNotSupportedException;

    public void takeattack(float attack_val){
        hp -= ((attack_val*3)/2);
    }

    public void attack(monster m){
        System.out.println("Sidekick attacked and inflicted " +
                damage +" damage to the monster.");
        m.takeattack(damage);
    }

}

class minions extends sidekick implements Cloneable{
    private boolean can_clone;
    minions(int heroxp){
        super();
        type = 1;
        can_clone = true;
        xp = heroxp;
        float damageincrease = heroxp -5;
        damage = 1+ damageincrease/2;
    }
    @Override
    public ArrayList<sidekick> makeclones() throws CloneNotSupportedException {
        ArrayList<sidekick> clonelist = new ArrayList<>();
        if(can_clone == true){
            sidekick clone1 = (sidekick) this.clone();
            sidekick clone2 = (sidekick) this.clone();
            sidekick clone3 = (sidekick) this.clone();
            clonelist.add(clone1);
            clonelist.add(clone2);
            clonelist.add(clone3);
            can_clone = false;
        }
        else{
            System.out.println("Cannot clone now as you have already used" +
                    " the cloning ability");
        }
        return clonelist;
    }
    @Override
    public sidekick clone() throws CloneNotSupportedException {
        return (sidekick) super.clone();
    }


}

class knight extends sidekick{
    knight(int heroxp){
        super();
        type = 2;
        xp = heroxp;
        float damageincrese = heroxp - 8;
        damage =  2 + damageincrese/2;
    }
    @Override
    public ArrayList<sidekick> makeclones() throws CloneNotSupportedException{
        ArrayList<sidekick> clonelist = new ArrayList<>();
        return clonelist;
    }
}

abstract class monster{
    protected float hp;
    protected int level;

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }
    abstract int getLevel();

    public void setLevel(int level) {
        this.level = level;
    }
    public void takeattack(float attack_val){
        hp -= attack_val;
    }

    public void move(hero h,sidekick s){
        boolean sidekick_present = false;
        if(s!= null){
            sidekick_present = true;
        }
        Random r =new Random();
        float damage = (int) ((Math.abs(r.nextGaussian()))*(hp/4));
        damage/=2;
        System.out.println("Monster inflicted damage: "+damage);
        if(h.level == 2 && sidekick_present == true){
            if(s.getType() == 2) {
                damage -= 5;
            }
        }
        if(damage!=0) {
            if(sidekick_present == true){
                s.takeattack(damage);
            }
            h.takeattack(damage);
        }
    }
    abstract void afterfight();
}
class goblins extends monster{
    goblins(){
        hp = 100;
        level = 1;
    }
    @Override
    public int getLevel(){
        return level;
    }
    @Override
    public void afterfight(){
        hp = 100;
    }
}

class zombies extends monster{
    zombies() {
        hp = 100;
        level = 2;
    }
    @Override
    public int getLevel(){
        return level;
    }
    @Override
    public void afterfight(){
        hp = 100;
    }
}

class fiends extends monster{
    fiends(){
        hp = 200;
        level = 3;
    }
    @Override
    public int getLevel(){
        return level;
    }
    @Override
    public void afterfight(){
        hp = 100;
    }
}

class lionfang extends monster{
    lionfang(){
        hp = 250;
        level = 4;
    }
    @Override
    public int getLevel(){
        return level;
    }
    @Override
    public void move(hero h, sidekick s){
        boolean sidekick_present = false;
        if(s!= null){
            sidekick_present = true;
        }
        Random r = new Random();
        int special = r.nextInt(10);
        if(special == 1){
            System.out.println("Monster inflicted damage: "+hp/2);
            System.out.println("Monster Uses special Attack");
            h.takeattack(hp/2);
        }
        else{
            float damage = (int) ((Math.abs(r.nextGaussian()))*(hp/4));
            damage/=2;
            System.out.println("Monster inflicted damage: "+damage);
            if(damage!=0) {
                if(sidekick_present == true){
                    s.takeattack(damage);
                }
                h.takeattack(damage);
            }
        }
    }
    @Override
    void afterfight() {
        System.out.println("LIONFANG IS DEFEATED!!!");
    }
}

class Graph
{
    int V;
    LinkedList<Integer> adjListArray[];
    HashMap<Integer,monster> Array_monsters = new HashMap<>();
    Graph(int V)
    {
        this.V = V;
        adjListArray = new LinkedList[V];
        Random r = new Random();
        for(int i = 0; i < V ; i++){

            adjListArray[i] = new LinkedList<>();
            int type = r.nextInt(3);
            if(type == 0){
                monster temp = new goblins();
                Array_monsters.put(i,temp);
            }
            if(type == 1){
                monster temp = new zombies();
                Array_monsters.put(i,temp);
            }
            else{
                monster temp = new fiends();
                Array_monsters.put(i,temp);
            }
        }
        monster temp = new lionfang();
        Array_monsters.put(7,temp);
    }
    LinkedList<Integer> getneigbours(int curr){
        return adjListArray[curr];
    }
    void addEdge(int src, int dest)
    {
        adjListArray[src].add(dest);
    }
    monster getcurrentmonster(int curr){
        if(Array_monsters.get(curr) == null){
            monster temp =  new goblins();
            return temp;
        }
        return Array_monsters.get(curr);
    }
}
public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static int mainmenu() throws IOException {
        System.out.println("Welcome to ArchLegends");
        System.out.println("Choose your option");
        System.out.println("1) New User");
        System.out.println("2) Exixting User");
        System.out.println("3) Exit");
        int choice = Integer.parseInt(br.readLine());
        return choice;
    }
    public static user createhero() throws IOException {
        System.out.println("Enter the Username");
        String name = br.readLine();
        System.out.println("Choose Hero type");
        System.out.println("1) Warrior");
        System.out.println("2) Theif");
        System.out.println("3) Mage");
        System.out.println("4) Healer");
        int type = Integer.parseInt(br.readLine());
        user temp = new user(name,type);
        String ty;
        if(type == 1){
            ty = "Warrior";
        }
        else if(type == 2){
            ty = "Theif";
        }
        else if(type == 3){
            ty = "Mage";
        }
        else{
            ty = "Healer";
        }
        System.out.println("User Creation done. Username: "+name+" Hero type: "+ty+" . Log in to play the game . Exiting");

        return temp;
    }
    public static int chooselocation(int curr,Graph gr) throws IOException {
        System.out.println("Choose path");
        LinkedList<Integer> locations = gr.getneigbours(curr);
        for(int i = 1;i<locations.size()+1;i++) {
            System.out.println(i+") Go to Location "+locations.get(i-1));
        }
        System.out.println("Enter -1 to exit");
        int choice = Integer.parseInt(br.readLine());
        if(choice == 1){
            System.out.println("Moving to location "+locations.get(0));
            return locations.get(0);
        }
        else if(choice == 2){
            System.out.println("Moving to location "+ locations.get(1));
            return locations.get(1);
        }
        else if(choice == 3){
            System.out.println("Moving to location "+ locations.get(2));
            return locations.get(2);
        }
        else return -1;
    }
    public static int choosefightmove(boolean sp) throws IOException {
        if(sp == false) {
            System.out.println("Choose move: \n1)Attack \n2)Defense");
        }
        else{
            System.out.println("Choose move: \n1)Attack \n2)Defense \n3)Special Power");
        }
        return Integer.parseInt(br.readLine());
    }
    public static boolean fight(monster m,hero h) throws IOException, CloneNotSupportedException {
        String use_sidekick = "no";
        if(h.total_sidekick() != 0) {
            System.out.println("Type yes if you wish to use a " +
                    "sidekick, else type no");
            use_sidekick = br.readLine();
        }
        boolean sidekick_present = false;
        sidekick mysidekick = null;
        ArrayList<sidekick> clones = new ArrayList<>();
        boolean clone_available = false;
        if(use_sidekick.equals("yes")){
            sidekick_present = true;
            mysidekick = h.usesidekick();
            String sidekickname;
            if(mysidekick.getType() == 1){
                sidekickname = "Minion";
            }
            else{
                sidekickname = "Knight";
            }
            System.out.println("You have a sidekick "+sidekickname+" " +
                    "with you.");
            System.out.println("Attack of sidekick is "+mysidekick.getDamage());
            if(mysidekick.getType() == 1){
                System.out.println("Press 1 to use the cloning ability. " +
                        "Else press 2 to move to the fight");
                int minion_ability = Integer.parseInt(br.readLine());
                if(minion_ability == 1){
                    //make clones
                    clones.addAll(mysidekick.makeclones());
                    if(clones.size()!=0){
                        clone_available = true;
                    }
                    System.out.println("Cloning done");
                }
            }
        }
        int count = 0;
        boolean sp = false;
        while(h.getHp() > 0 && m.getHp()>0){
            if(sidekick_present == true) {
                if (mysidekick.getHp() < 0) {
                    sidekick_present = false;
                    System.out.println("You lost your sidekick");
                }
            }
            count++;
            if(count >= 4){
                sp = true;
            }
            int current_move = choosefightmove(sp);
            if(current_move == 1){
                System.out.println("You choose to attack");
                h.move(1,m);
                if(sidekick_present == true){
                    if(clone_available == true){
                        clones.get(0).attack(m);
                        clones.get(1).attack(m);
                        clones.get(2).attack(m);
                    }
                    mysidekick.attack(m);
                }

            }
            else if(current_move == 2){
                System.out.println("You choose to defense");
                h.move(2,m);

            }
            else if(current_move == 3){
                System.out.println("Special Power activated");
                h.move(3,m);
                sp = false;
                count = 0;
            }
            System.out.println("Your HP: "+h.getHp()+" Monster's HP: "+m.getHp());
            if(sidekick_present == true) {
                mysidekick.printHp();
                if (clone_available == true) {
                    clones.get(0).printHp();
                    clones.get(1).printHp();
                    clones.get(2).printHp();
                }
            }
            if(m.getHp()>0) {
                System.out.println("Monster Attacks");
                if (sidekick_present == true) {
                    m.move(h, mysidekick);
                    if (clone_available == true) {
                        clones.get(0).setHp(mysidekick.getHp());
                        clones.get(1).setHp(mysidekick.getHp());
                        clones.get(2).setHp(mysidekick.getHp());
                    }
                }
                else
                    m.move(h, mysidekick);
                System.out.println("Your HP: " + h.getHp() + " Monster's HP: " + m.getHp());
                if(sidekick_present == true) {
                    mysidekick.printHp();
                    if (clone_available == true) {
                        clones.get(0).printHp();
                        clones.get(1).printHp();
                        clones.get(2).printHp();
                    }
                }
            }
        }
        if(sidekick_present == true) {
            if (mysidekick.getHp() > 0) {
                mysidekick.afterfight(m.getLevel());
                h.placeback_sidekick(mysidekick);
            }
        }
        if(h.getHp()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public static void getnewsidekick(hero h ) throws IOException{
        System.out.println("Your current XP: "+ h.getXp());
        System.out.println("If you want to but a minion, press 1");
        System.out.println("If you want to but a Knight, press 2");
        int sidekickchoice = Integer.parseInt(br.readLine());
        System.out.print("XP to spend: ");
        int xp_to_spend = Integer.parseInt(br.readLine());
        String sidekickname;
        if(sidekickchoice == 1){
            sidekickname = "Minion";
        }
        else{
            sidekickname = "Knight";
        }
        if(h.getXp() > xp_to_spend){
            System.out.println("You bought sidekick: "+sidekickname);
            h.addsidekick(sidekickchoice,xp_to_spend);
            System.out.println("XP of sidekick: 0");
            sidekick temp = h.getsidekick();
            System.out.println("Attack of the sidekick: "+temp.getDamage());
        }
        else{
            System.out.println("You cannot buy sidekick "+ sidekickname);
        }
        System.out.println("You want to buy more Sidekick[y/n] ?");
        String more = br.readLine();
        if(more.equals("y")){
            getnewsidekick(h);
        }

    }
    public static void playgame(user current,Graph gr) throws IOException, CloneNotSupportedException {
        hero myhero = null;
        if(current.getHerotype() == 1){
            myhero = new warrior(current.getUsername());
        }
        else if(current.getHerotype() == 2){
            myhero = new theif(current.getUsername());
        }
        else if(current.getHerotype() == 3){
            myhero = new mage(current.getUsername());
        }
        else if(current.getHerotype() == 4){
            myhero = new healer(current.getUsername());
        }

        System.out.println("You are at Starting point.");
        int curr_location = 0;
        int count = 0;
        while(curr_location!=7){
            boolean buysidekick = false;
            if(count>0){
                System.out.println("If you would you like to " +
                        "buy a sidekick, type yes. Else type no to upgrade level.");
                String side_ans = br.readLine();
                if(side_ans.equals("yes")){
                    //add new sidekick
                    buysidekick = true;
                    getnewsidekick(myhero);
                    myhero.printlist();
                    System.out.println("Your hero level is now: "+ myhero.level);
                }
                else{
                    System.out.println("Your hero level is now: "+ myhero.level);
                }
            }
            curr_location = chooselocation(curr_location,gr);
            if(curr_location == -1) break;
            monster temp = gr.getcurrentmonster(curr_location);

            if(temp.getLevel() == 4){
                System.out.println("Boss Fight, You are fighting the final Boss: LIONFANG!!!");
            }
            else {
                System.out.println("Fight Started. You are fighting monster of level " + temp.getLevel());
            }
            boolean win_loss = fight(temp,myhero);
            if(win_loss == true && curr_location != 7){
                System.out.println("Fight won. Preceed to the next location");
            }
            else if(win_loss == false && curr_location!=7){
                System.out.println("Fight lost. Resurrect at the initial point");
                curr_location = 0;
            }
            myhero.afterfight(win_loss,temp);
            temp.afterfight();
            count +=1;

        }
        if(curr_location == 7) {
            System.out.println("CONGRATULATIONS!!! You have won the Game");
        }
    }

    public static void makegraph(Graph gr){
        gr.addEdge(0,1);
        gr.addEdge(0,2);
        gr.addEdge(0,3);
        gr.addEdge(1,2);
        gr.addEdge(1,4);
        gr.addEdge(1, 3);
        gr.addEdge(2,1);
        gr.addEdge(2,4);
        gr.addEdge(2,6);
        gr.addEdge(3,4);
        gr.addEdge(3,2);
        gr.addEdge(6,4);
        gr.addEdge(3,1);
        gr.addEdge(4,5);
        gr.addEdge(4,6);
        gr.addEdge(4,2);
        gr.addEdge(5,1);
        gr.addEdge(5,3);
        gr.addEdge(5,7);
        gr.addEdge(7,8);
    }
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
	// write your code here

        HashMap<String,user> database = new HashMap<>();
        Graph gr = new Graph(8);
        makegraph(gr);
        int mainmenu_choice = mainmenu();
        while(mainmenu_choice != 3){
            if(mainmenu_choice == 1){
                //New User
                user temp = createhero();
                database.put(temp.getUsername(),temp);
            }
            else if(mainmenu_choice == 2){
                //Existing User
                System.out.println("Enter Username: ");
                String name = br.readLine();
                if(database.containsKey(name)){
                    //play game
                    System.out.println("User Found. Logging in");
                    user current_user = database.get(name);
                    playgame(current_user,gr);
                }
                else{
                    //Wrong username
                    System.out.println("Username Entered does not exist");
                }
            }
            mainmenu_choice = mainmenu();
        }
    }
}
