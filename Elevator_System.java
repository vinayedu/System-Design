import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Elevator_System {
    int numFloors;
    int currFloor;
    Direction currDir;
    HashMap<String, HashMap<Integer,Integer>> map;
    HashSet<Integer> destinations;

    public Elevator_System(int numFloors, int currFloor){
        this.currFloor = currFloor;
        this.numFloors = numFloors;
        this.currDir = Direction.UP;
        this.map = new HashMap<>();
        map.put("UP",new HashMap<>());
        map.put("DOWN",new HashMap<>());
        this.destinations = new HashSet<>();
    }

    public void addRequest(Request req){
        HashMap<Integer, Integer> tmpMap = req.direction == Direction.UP ? map.get("UP") : map.get("DOWN");
        tmpMap.put(req.srcFloor,tmpMap.getOrDefault(req.srcFloor,0)+1);
    }

    public int look_ahead(Direction dir, int currFloor){

        if(dir == Direction.UP){
            for(int floor = currFloor+1; floor < numFloors; floor++){
                if(map.get("UP").getOrDefault(floor,0) != 0 || destinations.contains(floor)){
                    return floor;
                }
            }
            this.currDir = Direction.DOWN;
        }else{
            for(int floor = currFloor-1; floor >= 0; floor--){
                if(map.get("DOWN").getOrDefault(floor,0) != 0 || destinations.contains(floor)){
                    return floor;
                }
            }
            this.currDir = Direction.UP;
        }
        return currFloor;

    }

    public void process_requests(){
        while(true){
            int toGoFloor = look_ahead(this.currDir,this.currFloor);
            if(toGoFloor != this.currFloor){
                System.out.println("Reached the floor" + toGoFloor);
            }
            destinations.remove(toGoFloor);
            if(this.currDir == Direction.UP && this.map.get("UP").getOrDefault(toGoFloor,0) != 0){
                this.map.get("UP").put(toGoFloor,0);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter destination floors separated with space");
                String[] inp = scanner.nextLine().split(" ");
                for(int i=0; i<inp.length; i++) destinations.add(Integer.parseInt(inp[i]));

            }else if(this.currDir == Direction.DOWN && this.map.get("DOWN").getOrDefault(toGoFloor,0) != 0){
                this.map.get("DOWN").put(toGoFloor,0);
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter destination floors separated with space");
                String[] inp = scanner.nextLine().split(" ");
                for(int i=0; i<inp.length; i++) destinations.add(Integer.parseInt(inp[i]));
            }

            this.currFloor = toGoFloor;
        }


    }


}
