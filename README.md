# 3303-Elevator-Control-System

## About the project
A soon-to-be, full-fledged concurrent elevator control system and simulator that will run on three separate computers over LAN. This simulator is based on real-life measurements taken from the elevators in the Minto CASE Building at Carleton University.
# 3303-Elevator-Control-System

## About the project
This project aims to be a multi-threaded elevator control system simulator that will run on three separate computers over LAN. This simulator will be based on real-life measurements taken from the elevators in the Minto CASE Building at Carleton University.

## Set-up
  Import the 3303-Elevator-Control-System folder into Eclipse
  Run the ElevatorSubSystem,Scheduler and FloorSubSystem class.
  
  Note: Scheduler and Elevator should be running before the FloorSubSystem

## The team
Jake Cassady (jakecassady)

Cameron Davis (aeiou80)

Andrew Foster (Ajfoster4444)

Eric Vincent (EricOfTweed)

John Warde (Imcool4789)

## Team roles
**Iteration 2**

  Jake Cassady: Test Cases
  
  Cameron Davis: State Diagrams
  
  Andrew Foster: FloorSubSystem and Elevator State Machine Implementation
  
  Eric Vincent: Scheduler State Machine, README
  
  John Warde: UML Diagrams, Sequence Diagram, Test Cases
  
## Classes
 Elevator: The elevator object, receives info and travels to specified floors.
 
 ElevatorSubSystem: initializes the Elevator(s) and runs their start methods.
 
 EndState: An Elevator State in which the Elevator will do nothing.
 
 Floor: An Object that represents a floor of a bui;ding with a set floor number.
 
 FloorButton: The up or down button on each floor.
 
 FloorLamp: The lamps for the up and down buttons, are active when the FloorButton is pressed.
 
 FloorSubSystem: The subsytem responsible for reading in requests and sending them off to the scheduler.
 
 inforElevatorSystem: Stores info on the elevator system(s), such as their port number and current floor.
 
 MoveState: An Elevator State for when the Elevator is moving, either up or down.
 
 readFile: Provides the static method that allows a file to be read in.
 
 Scheduler: When in READINGFROMFLOOR State, messages are received from the floor subsystem and sent to the elevator. The opposite occures in READINGFROMELEVATOR State.
 
 ScheduleStateEnum: Constant Enums for the States of the scheduler.
 
 StateE: Abstract class that all Elevator States extend.
 
 StopState: An Elevator State that occurs when the Elevator reaches the floor, and allows door to open and close.
 
 test:
  
## The team
Jake Cassady (jakecassady)

Cameron Davis (aeiou80)

Andrew Foster (Ajfoster4444)

Eric Vincent (EricOfTweed)

John Ward (Imcool4789)
