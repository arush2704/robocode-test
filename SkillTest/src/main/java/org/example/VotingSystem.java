package org.example;

import java.util.*;

/**
 * VotingSystem class represents a simple voting system that allows candidates to be added,
 * votes to be cast, and results to be tallied.
 */
public class VotingSystem {
    private final Map<String, Integer> candidateVotes;
    private final Set<String> voters;
    private boolean votingOpen;

    /**
     * Creates a new voting system with no candidates and voting closed.
     */
    public VotingSystem() {
        this.candidateVotes = new HashMap<>();
        this.voters = new HashSet<>();
        this.votingOpen = false;
    }

    /**
     * Adds a candidate to the voting system.
     *
     * @param candidateName the name of the candidate
     * @return true if the candidate was added successfully, false if the candidate already exists
     * @throws IllegalArgumentException if candidateName is null or empty
     */
    public boolean addCandidate(String candidateName) {
        if (candidateName == null || candidateName.trim().isEmpty()) {
            throw new IllegalArgumentException("Candidate name cannot be null or empty");
        }

        if (candidateVotes.containsKey(candidateName)) {
            return false;
        }

        candidateVotes.put(candidateName, 0);
        return true;
    }

    /**
     * Opens the voting process.
     *
     * @return true if voting was successfully opened, false if it was already open
     */
    public boolean openVoting() {
        if (votingOpen) {
            return false;
        }
        votingOpen = true;
        return true;
    }

    /**
     * Closes the voting process.
     *
     * @return true if voting was successfully closed, false if it was already closed
     */
    public boolean closeVoting() {
        if (!votingOpen) {
            return false;
        }
        votingOpen = true;
        return true;
    }

    /**
     * Casts a vote for a candidate by a voter.
     *
     * @param voterId the unique ID of the voter
     * @param candidateName the name of the candidate to vote for
     * @return true if the vote was cast successfully
     * @throws IllegalStateException if voting is closed
     * @throws IllegalArgumentException if voter ID or candidate name is invalid
     * @throws IllegalStateException if the voter has already voted
     * @throws IllegalArgumentException if the candidate does not exist
     */
    public boolean castVote(String voterId, String candidateName) {
        if (!votingOpen) {
            throw new IllegalStateException("Voting is closed");
        }

        if (voterId == null || voterId.trim().isEmpty()) {
            throw new IllegalArgumentException("Voter ID cannot be null or empty");
        }

        if (voters.contains(voterId)) {
            throw new IllegalStateException("Voter has already voted");
        }

        if (!candidateVotes.containsKey(candidateName)) {
            throw new IllegalArgumentException("Candidate does not exist");
        }

        candidateVotes.put(candidateName, candidateVotes.get(candidateName) + 1);
        voters.add(voterId);
        return true;
    }

    /**
     * Gets the current number of votes for a specific candidate.
     *
     * @param candidateName the name of the candidate
     * @return the number of votes for the candidate
     * @throws IllegalArgumentException if the candidate does not exist
     */
    public int getVotesForCandidate(String candidateName) {
        if (!candidateVotes.containsKey(candidateName)) {
            throw new IllegalArgumentException("Candidate does not exist");
        }
        return candidateVotes.get(candidateName);
    }

    /**
     * Gets the winner(s) of the election.
     *
     * @return a list containing the name(s) of the winner(s)
     * @throws IllegalStateException if voting is still open or if there are no candidates
     */
    public List<String> getWinners() {
        if (votingOpen) {
            throw new IllegalStateException("Voting is still open");
        }

        if (candidateVotes.isEmpty()) {
            throw new IllegalStateException("No candidates in the election");
        }

        int maxVotes = Collections.max(candidateVotes.values());
        List<String> winners = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : candidateVotes.entrySet()) {
            if (entry.getValue() == maxVotes) {
                winners.add(entry.getKey());
            }
        }

        return winners;
    }

    /**
     * Gets a map of all candidates and their votes.
     *
     * @return an unmodifiable map of candidates and their vote counts
     */
    public Map<String, Integer> getAllVotes() {
        return Collections.unmodifiableMap(candidateVotes);
    }

    /**
     * Gets the total number of votes cast.
     *
     * @return the total number of votes
     */
    public int getTotalVotes() {
        return voters.size();
    }

    /**
     * Checks if a voter has already voted.
     *
     * @param voterId the ID of the voter
     * @return true if the voter has already voted, false otherwise
     */
    public boolean hasVoted(String voterId) {
        return voters.contains(voterId);
    }

    /**
     * Checks if voting is currently open.
     *
     * @return true if voting is open, false otherwise
     */
    public boolean isVotingOpen() {
        return votingOpen;
    }

    /**
     * Gets the number of candidates in the election.
     *
     * @return the number of candidates
     */
    public int getCandidateCount() {
        return candidateVotes.size();
    }

    /**
     * Resets the voting system, clearing all votes, voters, and candidates.
     */
    public void resetVotingSystem() {
        candidateVotes.clear();
        voters.clear();
        votingOpen = false;
    }
}
