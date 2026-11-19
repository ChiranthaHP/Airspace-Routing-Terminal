# Airspace Routing Terminal

An object-oriented Java console application for flight dispatch and logistics calculations. It computes estimated times of arrival (ETAs), fuel requirements, refueling stops, and total operational costs across multiple aircraft classes.

## Key Features
- **Object-Oriented Architecture:** Uses an inheritance hierarchy (`FlightPlan` parent class with specialized aircraft subclasses) and encapsulation to keep calculation logic modular and maintainable.
- **Polymorphic Dispatching:** Manages diverse flight plans in a unified `ArrayList` dispatcher to generate consolidated flight manifests without redundant conditional logic.
- **Logistics & Cost Analysis:** Calculates trip duration, fuel burn, required refueling intervals, and operational expenses based on route distance and aircraft specifications.

## Project Structure
- `Project.java` — Main application, OOP class hierarchy, and flight dispatch logic.
- `In.java` — Console input utility class.

## How to Run
Make sure you have the Java Development Kit (JDK) installed, then compile and run from your terminal:

```bash
javac In.java Project.java
java Project
