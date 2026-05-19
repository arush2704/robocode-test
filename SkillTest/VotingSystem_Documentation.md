# Voting System Documentation

## Overview

This document describes a simple voting system implementation that allows for managing candidates, recording votes, and determining election winners. The system maintains voter integrity by preventing duplicate votes and provides a comprehensive API for election management.

## Features

- Add candidates to the election
- Open and close voting periods
- Cast votes for candidates
- Prevent duplicate voting with voter ID tracking
- Calculate election results and determine winners
- Handle tie situations
- Provide election statistics (vote counts, participation)
- Reset the entire voting system

## API Reference

### VotingSystem Class

#### Constructor

```java
public VotingSystem()
```
Creates a new voting system with no candidates and voting closed.

#### Managing Candidates

```java
public boolean addCandidate(String candidateName)
```
Adds a candidate to the election. Returns true if successful, false if the candidate already exists.

```java
public int getCandidateCount()
```
Returns the number of candidates in the election.

#### Managing Voting Status

```java
public boolean openVoting()
```
Opens the voting process. Returns true if voting was successfully opened, false if it was already open.

```java
public boolean closeVoting()
```
Closes the voting process. Returns true if voting was successfully closed, false if it was already closed.

```java
public boolean isVotingOpen()
```
Returns true if voting is currently open, false otherwise.

#### Voting

```java
public boolean castVote(String voterId, String candidateName)
```
Records a vote from a specific voter for a specific candidate. Returns true if the vote was successfully recorded.

```java
public boolean hasVoted(String voterId)
```
Checks if a voter has already cast a vote. Returns true if they have, false otherwise.

#### Results and Statistics

```java
public int getVotesForCandidate(String candidateName)
```
Returns the number of votes for a specific candidate.

```java
public List<String> getWinners()
```
Returns a list of winners (candidates with the highest number of votes). May contain multiple names in case of a tie.

```java
public Map<String, Integer> getAllVotes()
```
Returns an unmodifiable map containing all candidates and their vote counts.

```java
public int getTotalVotes()
```
Returns the total number of votes cast.

#### System Management

```java
public void resetVotingSystem()
```
Resets the entire voting system, clearing all votes, voters, and candidates.

## Usage Example

```java
// Create a new voting system
VotingSystem votingSystem = new VotingSystem();

// Add candidates
votingSystem.addCandidate("Candidate A");
votingSystem.addCandidate("Candidate B");
votingSystem.addCandidate("Candidate C");

// Open voting
votingSystem.openVoting();

// Cast votes
votingSystem.castVote("voter1", "Candidate A");
votingSystem.castVote("voter2", "Candidate B");
votingSystem.castVote("voter3", "Candidate A");

// Close voting
votingSystem.closeVoting();

// Get results
List<String> winners = votingSystem.getWinners();
System.out.println("Winners: " + winners);

Map<String, Integer> allVotes = votingSystem.getAllVotes();
System.out.println("All votes: " + allVotes);

System.out.println("Total votes: " + votingSystem.getTotalVotes());
```

## Error Handling

The voting system implements thorough error checking to maintain election integrity:

- Prevents adding null or empty candidate names
- Prevents duplicate candidates
- Restricts voting to only when voting is open
- Prevents voters from voting multiple times
- Ensures votes can only be cast for existing candidates
- Prevents calculating results before voting is closed

## Technical Implementation

The system uses:
- `HashMap` to track candidates and their vote counts
- `HashSet` to track voters who have already voted
- Boolean flag to track if voting is open or closed
- Collections API to determine winners and manage data

## Test Cases

The voting system includes comprehensive test cases that validate:
- Adding candidates (including edge cases)
- Opening and closing voting
- Vote casting with various scenarios
- Result calculation
- System reset functionality
- Error handling for all operations

## Limitations and Future Improvements

- Currently, the system runs in-memory with no persistence
- No authentication mechanism for voters
- No support for ranked-choice voting
- No administrative privileges or audit logs

Future versions could add:
- Database integration for persistence
- Secure voter authentication
- Support for different voting methods
- Administrative features for election management
- Comprehensive logging and audit trails
