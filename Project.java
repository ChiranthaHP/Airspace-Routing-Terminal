import java.util.ArrayList;

public class Project {
    public static void main(String[] args) {
        
        FlightDispatcher dispatcher = new FlightDispatcher();
        
        // --- TERMINAL GRAPHICS & INSTRUCTIONS ---
        System.out.println("\n==================================================");
        System.out.println("            AIRSPACE ROUTING TERMINAL");
        System.out.println("==================================================");
        System.out.println(" ROUTES AVAILABLE (Shortest to Longest):");
        System.out.println("  [1] CMB (Colombo) -> MLE (Male)      |  770 km");
        System.out.println("  [2] CMB (Colombo) -> SIN (Singapore) | 2740 km");
        System.out.println("  [3] CMB (Colombo) -> DXB (Dubai)     | 3280 km");
        System.out.println(" AIRCRAFT AVAILABLE (Shortest to Longest Range):");
        System.out.println("  [1] Bell 407 (HELO)              | Range: 600 km");
        System.out.println("  [2] Cessna 172 (Light Aircraft)  | Range: 1200 km");
        System.out.println("  [3] Boeing 777 (Heavy Airliner)  | Range: 13600 km");
        System.out.println("==================================================\n");
        
        // Loop to process multiple flight plans
        for (int i = 1; i <= 3; i++) {
            System.out.println("--- LOGGING DISPATCH " + i + " ---");
            
            System.out.print("Select Route (1, 2, or 3): ");
            int routeChoice = In.nextInt();
            
            System.out.print("Select Aircraft (1, 2, or 3): ");
            int aircraftChoice = In.nextInt();
            
            String selectedRoute;
            double distance;
            
            // Selection logic for 3 routes
            if (routeChoice == 1) {
                selectedRoute = "CMB -> MLE";
                distance = 770.0;
            } else if (routeChoice == 2) {
                selectedRoute = "CMB -> SIN";
                distance = 2740.0;
            } else {
                selectedRoute = "CMB -> DXB";
                distance = 3280.0;
            }
            
            // Selection logic for 3 aircraft types
            if (aircraftChoice == 1) {
                HelicopterPlan hPlan = new HelicopterPlan(selectedRoute, distance, "Twin-Blade");
                dispatcher.logPlan(hPlan);
                System.out.println("[+] HELO Operations plan calculated and logged.\n");
            } else if (aircraftChoice == 2) {
                PrivatePlan pPlan = new PrivatePlan(selectedRoute, distance, "VFR");
                dispatcher.logPlan(pPlan);
                System.out.println("[+] Light Aircraft Dispatch calculated and logged.\n");
            } else {
                CommercialPlan cPlan = new CommercialPlan(selectedRoute, distance, "UL303");
                dispatcher.logPlan(cPlan);
                System.out.println("[+] Heavy Airliner Dispatch calculated and logged.\n");
            }
        }
        
        System.out.println("==================================================");
        System.out.println("            AIRSPACE ROUTING MANIFEST");
        System.out.println("==================================================");
        dispatcher.printManifest();
        System.out.println("==================================================\n");
    }
}

// Parent Class
class FlightPlan { 
    // Encapsulation: private attributes
    private String routeName;
    private double routeDistance;
    private double aircraftSpeed;
    private double fuelBurnRate;
    private int cruisingAltitude;
    private double maxRange; 
    private double fuelPricePerLiter; 

    // Constructor 
    FlightPlan(String routeName, double routeDistance, double aircraftSpeed, double fuelBurnRate, int cruisingAltitude, double maxRange, double fuelPricePerLiter) {
        this.routeName = routeName;
        this.routeDistance = routeDistance;
        this.aircraftSpeed = aircraftSpeed;
        this.fuelBurnRate = fuelBurnRate;
        this.cruisingAltitude = cruisingAltitude;
        this.maxRange = maxRange;
        this.fuelPricePerLiter = fuelPricePerLiter;
    }

    public double calculateETA() {
        return routeDistance / aircraftSpeed; 
    }

    public double calculateTotalFuel() {
        return calculateETA() * fuelBurnRate; 
    }

    public int calculateStops() {
        int stops = (int) (routeDistance / maxRange);
        if (routeDistance % maxRange == 0) {
            stops = stops - 1;
        }
        return stops;
    }

    public double calculateTotalCost() {
        return calculateTotalFuel() * fuelPricePerLiter;
    }

    @Override
    public String toString() {
        return "Route: " + routeName + " | Alt: " + cruisingAltitude + " ft\n" +
               "      -> Distance: " + routeDistance + " km\n" +
               "      -> Est. Time: " + calculateETA() + " hours\n" +
               "      -> Refueling Stops: " + calculateStops() + "\n" +
               "      -> Fuel Req: " + calculateTotalFuel() + " Liters\n" +
               "      -> Est. Cost: $" + String.format("%.2f", calculateTotalCost());
    }
}

// Child Class 1 (Mapped to Option 1)
class HelicopterPlan extends FlightPlan {
    private String rotorType;

    HelicopterPlan(String routeName, double routeDistance, String rotorType) {
        super(routeName, routeDistance, 240.0, 110.0, 3000, 600.0, 0.80);
        this.rotorType = rotorType;
    }

    @Override
    public String toString() {
        return "[HELO OPS - Bell 407] Rotor: " + rotorType + "\n      -> " + super.toString() + "\n";
    }
}

// Child Class 2 (Mapped to Option 2)
class PrivatePlan extends FlightPlan { 
    private String flightRules; 

    PrivatePlan(String routeName, double routeDistance, String flightRules) {
        super(routeName, routeDistance, 226.0, 32.0, 10000, 1200.0, 2.50); 
        this.flightRules = flightRules;
    }

    @Override
    public String toString() {
        return "[LIGHT AIRCRAFT - Cessna] Rules: " + flightRules + "\n      -> " + super.toString() + "\n";
    }
}

// Child Class 3 (Mapped to Option 3)
class CommercialPlan extends FlightPlan { 
    private String flightNumber;

    CommercialPlan(String routeName, double routeDistance, String flightNumber) {
        super(routeName, routeDistance, 900.0, 7500.0, 35000, 13600.0, 0.80); 
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return "[HEAVY AIRLINER - Boeing] Flight: " + flightNumber + "\n      -> " + super.toString() + "\n";
    }
}

// Standalone Class
class FlightDispatcher { 
    private ArrayList<FlightPlan> activePlans;

    FlightDispatcher() {
        activePlans = new ArrayList<FlightPlan>();
    }

    void logPlan(FlightPlan newPlan) {
        if (newPlan != null) { 
            activePlans.add(newPlan);
        }
    }

    void printManifest() {
        for (int i = 0; i < activePlans.size(); i++) {
            System.out.println(activePlans.get(i)); 
        }
    }
}