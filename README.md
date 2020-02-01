# 3303-Elevator-Control-System

## About the project
This project aims to be a multi-threaded elevator control system simulator that will run on three separate computers over LAN. This simulator will be based on real-life measurements taken from the elevators in the Minto CASE Building at Carleton University.

## Set-up
  Import the 3303-Elevator-Control-System folder into Eclipse and run it from the main.Start class
  
  Run the test.SystemTests to run the test

## The team
Jake Cassady (jakecassady)

Cameron Davis (aeiou80)

Andrew Foster (Ajfoster4444)

Eric Vincent (EricOfTweed)

John Warde (Imcool4789)

## Team roles
**Iteration 1**

  Jake Cassady: Elevator subsystem
  
  Cameron Davis: Elevator subsystem, Scheduler and README.txt
  
  Andrew Foster: Floor subsystem
  
  Eric Vincent: Floor subsystem, JUnit test
  
  John Warde: UML Diagrams, Constant classes, Scheduler
  
## Classes
  constants.FloorButton: enum for up/down floor buttons
  
  constants.FloorLevel: enum for every floor
  
  constants.TimeConst: time constants from elevator measurements
  
  constants.WaitTime: class for sub system threads to call in order to sleep
  
  elevator.ElevatorSubSystem: receives information from the scheduler sub system and sends it back
  
  floor.FloorData: data structure for info produced by floor sub system
  
  floor.FloorSubSystem: produces instructions for scheduler sub system
  
  main.Start: initializes and starts sub system threads
  
  scheduler.Scheduler: a communication link for elevator and floor sub system threads
  
  test.SystemTests: elevator system testing
  
