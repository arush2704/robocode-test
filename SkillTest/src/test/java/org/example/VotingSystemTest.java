package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the VotingSystem class.
 */
class VotingSystemTest {

    private VotingSystem votingSystem;

    @BeforeEach
    void setUp() {
        votingSystem = new VotingSystem();
    }

    @Test
    void testAddCandidate() {
        assertTrue(votingSystem.addCandidate("Candidate A"));
        assertEquals(1, votingSystem.getCandidateCount());

        // Test adding duplicate candidate
        assertFalse(votingSystem.addCandidate("Candidate A"));
        assertEquals(1, votingSystem.getCandidateCount());

        // Test adding null candidate
        assertThrows(IllegalArgumentException.class, () -> votingSystem.addCandidate(null));

        // Test adding empty candidate name
        assertThrows(IllegalArgumentException.class, () -> votingSystem.addCandidate(""));
        assertThrows(IllegalArgumentException.class, () -> votingSystem.addCandidate("   "));
    }

    @Test
    void testOpenAndCloseVoting() {
        // Test opening voting
        assertTrue(votingSystem.openVoting());
        assertTrue(votingSystem.isVotingOpen());

        // Test opening voting when already open
        assertFalse(votingSystem.openVoting());

        // Test closing voting
        assertTrue(votingSystem.closeVoting());

        // Test closing voting when already closed
        assertFalse(votingSystem.closeVoting());
    }

    @Test
    void testCastVote() {
        votingSystem.addCandidate("Candidate A");
        votingSystem.addCandidate("Candidate B");
        votingSystem.openVoting();

        // Test casting valid vote
        assertTrue(votingSystem.castVote("voter1", "Candidate A"));
        assertEquals(1, votingSystem.getVotesForCandidate("Candidate A"));
        assertEquals(0, votingSystem.getVotesForCandidate("Candidate B"));

        // Test casting vote when voting is closed
        votingSystem.closeVoting();
        assertThrows(IllegalStateException.class, () -> votingSystem.castVote("voter2", "Candidate B"));

        // Reopen voting for further tests
        votingSystem.openVoting();

        // Test casting vote with null or empty voter ID
        assertThrows(IllegalArgumentException.class, () -> votingSystem.castVote(null, "Candidate B"));
        assertThrows(IllegalArgumentException.class, () -> votingSystem.castVote("", "Candidate B"));

        // Test voter voting twice
        assertThrows(IllegalStateException.class, () -> votingSystem.castVote("voter1", "Candidate B"));

        // Test voting for non-existent candidate
        assertThrows(IllegalArgumentException.class, () -> votingSystem.castVote("voter3", "Candidate C"));
    }

    @Test
    void testGetVotesForCandidate() {
        votingSystem.addCandidate("Candidate A");
        assertEquals(0, votingSystem.getVotesForCandidate("Candidate A"));

        // Test getting votes for non-existent candidate
        assertThrows(IllegalArgumentException.class, () -> votingSystem.getVotesForCandidate("Candidate B"));
    }

    @Test
    void testGetWinners() {
        // Test getting winners with no candidates
        votingSystem.openVoting();
        votingSystem.closeVoting();
        assertThrows(IllegalStateException.class, () -> votingSystem.getWinners());

        // Test getting winners with voting still open
        votingSystem.resetVotingSystem();
        votingSystem.addCandidate("Candidate A");
        votingSystem.addCandidate("Candidate B");
        votingSystem.openVoting();
        assertThrows(IllegalStateException.class, () -> votingSystem.getWinners());

        // Test single winner
        votingSystem.castVote("voter1", "Candidate A");
        votingSystem.castVote("voter2", "Candidate A");
        votingSystem.castVote("voter3", "Candidate B");
        votingSystem.closeVoting();
        List<String> winners = votingSystem.getWinners();
        assertEquals(1, winners.size());
        assertTrue(winners.contains("Candidate A"));

        // Test tie
        votingSystem.resetVotingSystem();
        votingSystem.addCandidate("Candidate A");
        votingSystem.addCandidate("Candidate B");
        votingSystem.openVoting();
        votingSystem.castVote("voter1", "Candidate A");
        votingSystem.castVote("voter2", "Candidate B");
        votingSystem.closeVoting();
        winners = votingSystem.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains("Candidate A"));
        assertTrue(winners.contains("Candidate B"));
    }

    @Test
    void testGetAllVotes() {
        votingSystem.addCandidate("Candidate A");
        votingSystem.addCandidate("Candidate B");
        votingSystem.openVoting();
        votingSystem.castVote("voter1", "Candidate A");
        votingSystem.castVote("voter2", "Candidate B");

        Map<String, Integer> votes = votingSystem.getAllVotes();
        assertEquals(2, votes.size());
        assertEquals(1, votes.get("Candidate A"));
        assertEquals(1, votes.get("Candidate B"));

        // Test that the returned map is unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> votes.put("Candidate C", 5));
    }

    @Test
    void testGetTotalVotes() {
        votingSystem.addCandidate("Candidate A");
        votingSystem.addCandidate("Candidate B");
        votingSystem.openVoting();

        assertEquals(0, votingSystem.getTotalVotes());

        votingSystem.castVote("voter1", "Candidate A");
        assertEquals(1, votingSystem.getTotalVotes());

        votingSystem.castVote("voter2", "Candidate B");
        assertEquals(2, votingSystem.getTotalVotes());
    }

    @Test
    void testHasVoted() {
        votingSystem.addCandidate("Candidate A");
        votingSystem.openVoting();

        assertFalse(votingSystem.hasVoted("voter1"));

        votingSystem.castVote("voter1", "Candidate A");
        assertTrue(votingSystem.hasVoted("voter1"));
    }

    @Test
    void testResetVotingSystem() {
        votingSystem.addCandidate("Candidate A");
        votingSystem.addCandidate("Candidate B");
        votingSystem.openVoting();
        votingSystem.castVote("voter1", "Candidate A");
        votingSystem.castVote("voter2", "Candidate B");

        votingSystem.resetVotingSystem();

        assertEquals(0, votingSystem.getCandidateCount());
        assertEquals(0, votingSystem.getTotalVotes());
        assertFalse(votingSystem.isVotingOpen());
        assertFalse(votingSystem.hasVoted("voter1"));
    }
}
