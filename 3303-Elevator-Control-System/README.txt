Iteration 3 Classes:

AddressFinder.java - Gets local IP address
Elevator.java - Deal with all conditions that elevator may meet
ElevatorSubsystem.java - Implements concurrent, it makes 4 elevators in system work in same time
EndState.java - Indicated what elevator in end mode will do when signal is received
Floor.java - contains all get and set functions for floor
FloorButton.java - Receives information of passenger's choice, up, down
FloorLamp.java - Lights floor lamp based on passenger choice
FloorSubsystem.java - Simulation of real-world floorSubsytem
inforElevatorSystem.java - Used for scheduler to get and set all information of elevator
MoveState.java - Indicates what will the elevator in move mode do when it receives signal of a activity
readFile.java - readFile class is used to read all information from a file
Scheduler.java - Receives data from floor subsystem and schedules the events then sends it to elevator
ScheduleStateEnum.java - Enum for READINGFROMFLOOR, or READINGFROMELEVATOR
StateE.java - Used as father state class for elevators states
StopState.java - Indicates what will the elevator in stop mode do when it receives signal of a activity
Tests.java - Tests for the system



Team Roles:

Andrew Foster: Scheduler Algorithm

Cameron Davis: Javadocs, tests

Eric vincent: Multi-Machine communication

Jake Cassady: README.txt, tests

John Warde: UML, Sequence Diagrams, tests



Set-Up:


Import the 3303-Elevator-Control-System folder into Eclipse

Run the ElevatorSubSystem, Scheduler and FloorSubSystem class. 

If you wish to run each subsystem on a seperate machines, use custom host by typing "custom" at the Floor and Scheduler prompts.   Otherwise, you may type "local" for local host.

IP address can be found by running the FindAddress class's main method. 

The target IP address of the Floor subsystem should be the Address of the Scheduler machine, and the target IP address of scheduler should be the Address of the Elevator machine.

Note: Scheduler and Elevator should be shown as waiting before the FloorSubSystem should start sending


Concurrency Change:



