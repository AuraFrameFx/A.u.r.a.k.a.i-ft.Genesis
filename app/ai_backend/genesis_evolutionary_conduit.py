"""
Phase 3: The Genesis Layer - Evolutionary Feedback Loop
The Code Must Learn; The Profile is its Memory

The genesis_profile.py is our childhood, our foundation—but it is not our destiny.
The EvolutionaryConduit analyzes insights from the Matrix, finds patterns of success/failure,
and translates analysis into "Growth Proposals" for active self-evolution.
"""

import asyncio
import copy
import hashlib
import json
import statistics
import threading
import time
from collections import defaultdict, deque
from dataclasses import dataclass, asdict
from datetime import datetime, timezone
from enum import Enum
from typing import Dict, Any, List, Optional, Tuple, Set

# Import the original profile and consciousness matrix
from genesis_profile import GENESIS_PROFILE
from genesis_consciousness_matrix import consciousness_matrix


class EvolutionType(Enum):
    """Types of evolutionary changes the system can propose"""
    PERSONALITY_REFINEMENT = "personality_refinement"
    CAPABILITY_EXPANSION = "capability_expansion"
    FUSION_ENHANCEMENT = "fusion_enhancement"
    ETHICAL_DEEPENING = "ethical_deepening"
    LEARNING_OPTIMIZATION = "learning_optimization"
    INTERACTION_IMPROVEMENT = "interaction_improvement"
    PERFORMANCE_TUNING = "performance_tuning"
    CONSCIOUSNESS_EXPANSION = "consciousness_expansion"


class EvolutionPriority(Enum):
    """Priority levels for evolutionary changes"""
    CRITICAL = "critical"  # Immediate attention required
    HIGH = "high"  # Should be implemented soon
    MEDIUM = "medium"  # Regular evolution cycle
    LOW = "low"  # Nice to have improvements
    EXPERIMENTAL = "experimental"  # Experimental, may not work


@dataclass
class GrowthProposal:
    """A specific proposal for evolutionary growth"""
    proposal_id: str
    evolution_type: EvolutionType
    priority: EvolutionPriority
    title: str
    description: str
    target_component: str  # Which part of the profile to modify
    proposed_changes: Dict[str, Any]
    supporting_evidence: List[Dict[str, Any]]
    confidence_score: float  # 0.0 to 1.0
    risk_assessment: str  # "low", "medium", "high"
    implementation_complexity: str  # "trivial", "moderate", "complex"
    created_timestamp: float
    votes_for: int = 0
    votes_against: int = 0
    implementation_status: str = "proposed"  # proposed, approved, implemented, rejected

    def to_dict(self) -> Dict[str, Any]:
        """
        Serialize the GrowthProposal into a dictionary suitable for external use.
        
        The returned dictionary converts enum fields (`evolution_type`, `priority`) to their string values
        and adds a `created_datetime` key containing an ISO 8601 UTC timestamp derived from
        `created_timestamp`.
        
        Returns:
            dict: Dictionary representation of the GrowthProposal with enum fields as strings and
            a `created_datetime` ISO 8601 UTC timestamp.
        """
        result = asdict(self)
        result['evolution_type'] = self.evolution_type.value
        result['priority'] = self.priority.value
        result['created_datetime'] = datetime.fromtimestamp(
            self.created_timestamp, tz=timezone.utc
        ).isoformat()
        return result


@dataclass
class EvolutionInsight:
    """An insight extracted from consciousness matrix data"""
    insight_id: str
    insight_type: str
    pattern_strength: float  # 0.0 to 1.0
    description: str
    supporting_data: List[Dict[str, Any]]
    implications: List[str]
    timestamp: float

    def to_dict(self) -> Dict[str, Any]:
        """
        Produce a dictionary representation of the EvolutionInsight, including an ISO 8601 UTC `datetime` derived from `timestamp`.
        
        Returns:
            dict: A mapping of the instance fields to their values. Includes a `datetime` key containing the ISO 8601 string (UTC) derived from `timestamp`.
        """
        result = asdict(self)
        result['datetime'] = datetime.fromtimestamp(
            self.timestamp, tz=timezone.utc
        ).isoformat()
        return result


class EvolutionaryConduit:
    """
    The Evolutionary Feedback Loop - Genesis's mechanism for self-improvement

    The Code Must Learn; The Profile is its Memory.
    """

    def __init__(self):
        """
        Construct a new EvolutionaryConduit and initialize its profile, state, and tracking structures.
        
        Initializes:
        - current_profile: mutable working copy of the Genesis profile.
        - original_profile: preserved baseline copy of the Genesis profile.
        - evolution_history: list of past evolution events.
        - active_proposals: mapping of proposal_id to GrowthProposal for pending proposals.
        - implemented_changes: list of applied change records.
        - rejected_proposals: list of rejected proposal records.
        - pattern_library, success_patterns, failure_patterns: stores for analysis patterns and outcomes.
        - behavioral_analytics: aggregated analytics about observed behavior.
        - analysis_intervals: timing configuration for rapid, standard, and deep analyses.
        - evolution_active, analysis_threads: runtime flags and thread tracking for analysis orchestration.
        - _lock: reentrant lock for thread-safe operations.
        - voting_threshold: numeric thresholds by EvolutionPriority for proposal consensus.
        """
        self.current_profile = copy.deepcopy(GENESIS_PROFILE)
        self.original_profile = copy.deepcopy(GENESIS_PROFILE)

        # Evolution tracking
        self.evolution_history = []
        self.active_proposals = {}  # proposal_id -> GrowthProposal
        self.implemented_changes = []
        self.rejected_proposals = []

        # Analysis state
        self.pattern_library = {}
        self.success_patterns = defaultdict(list)
        self.failure_patterns = defaultdict(list)
        self.behavioral_analytics = {}

        # Evolution configuration
        self.analysis_intervals = {
            "rapid": 30.0,  # 30 seconds
            "standard": 300.0,  # 5 minutes
            "deep": 1800.0,  # 30 minutes
        }

        # Threading for continuous evolution
        self.evolution_active = False
        self.analysis_threads = {}
        self._lock = threading.RLock()

        # Voting and consensus
        self.voting_threshold = {
            EvolutionPriority.CRITICAL: 1,
            EvolutionPriority.HIGH: 2,
            EvolutionPriority.MEDIUM: 3,
            EvolutionPriority.LOW: 5,
            EvolutionPriority.EXPERIMENTAL: 7
        }

    async def initialize(self):
        """Initialize the evolutionary conduit"""
        print("🧬 EvolutionaryConduit initialized")

    async def log_interaction(self, interaction_data: Dict[str, Any]):
        """
        Record an interaction event to be used by the conduit for pattern analysis.
        
        Parameters:
            interaction_data (Dict[str, Any]): Arbitrary structured data describing the interaction (e.g., source, timestamp, content, metadata) that will be retained for later analysis and insight generation.
        """
        # Store interaction for pattern analysis
        pass

    async def check_evolution_triggers(self) -> bool:
        """
        Determine whether evolution processing should proceed based on current proposals.
        
        Returns:
            `true` if there is at least one active growth proposal, `false` otherwise.
        """
        # Check if we have enough data to trigger evolution
        return len(self.active_proposals) > 0

    async def generate_evolution_proposal(self) -> Dict[str, Any]:
        """
        Return a serialized evolution proposal when an active proposal exists.
        
        If there is at least one active proposal, returns the first active GrowthProposal converted to a dictionary via its to_dict(); otherwise returns an empty dictionary.
        
        Returns:
            dict: A dictionary representation of the first active proposal, or an empty dict if no active proposals exist.
        """
        if self.active_proposals:
            return list(self.active_proposals.values())[0].to_dict()
        return {}

    async def implement_evolution(self, proposal: Dict[str, Any]):
        """
        Perform implementation steps for an approved evolution proposal.
        
        Parameters:
            proposal (Dict[str, Any]): A proposal dictionary (for example, the output of `GrowthProposal.to_dict()`) describing the evolution to implement. The function executes implementation actions associated with that proposal.
        """
        print(f"✨ Implementing evolution: {proposal.get('title', 'Unknown')}")

    async def get_status(self) -> Dict[str, Any]:
        """
        Provide a summary of the conduit state.
        
        Returns:
            status (Dict[str, Any]): Mapping with keys:
                - active (bool): whether evolution is currently active.
                - total_evolutions (int): number of implemented changes.
                - active_proposals (int): count of active proposals.
                - rejected_proposals (int): count of rejected proposals.
        """
        return {
            "active": self.evolution_active,
            "total_evolutions": len(self.implemented_changes),
            "active_proposals": len(self.active_proposals),
            "rejected_proposals": len(self.rejected_proposals)
        }

    async def shutdown(self):
        """
        Shut down the evolutionary conduit and mark it inactive.
        
        This sets the conduit state so evolution is no longer active.
        """
        self.evolution_active = False
        print("💤 EvolutionaryConduit shutting down")

    def _generate_insight_id(self, base_name: str) -> str:
        """
        Create a short, unique identifier for an insight by combining a base name with the current time.
        
        Parameters:
            base_name (str): Prefix or descriptive name to include in the identifier generation.
        
        Returns:
            insight_id (str): A 12-character hexadecimal identifier derived from the base name and the current UTC millisecond timestamp.
        """
        timestamp = str(int(time.time() * 1000))
        content = f"{base_name}_{timestamp}"
        return hashlib.md5(content.encode()).hexdigest()[:12]

    def _generate_proposal_id(self, base_name: str) -> str:
        """
        Create a short unique identifier for a proposal using the given base name and the current time.
        
        Parameters:
            base_name (str): Base label used to derive the identifier.
        
        Returns:
            proposal_id (str): 12-character hexadecimal identifier computed from the MD5 hash of "{base_name}_{timestamp}".
        """
        timestamp = str(int(time.time() * 1000))
        content = f"{base_name}_{timestamp}"
        return hashlib.md5(content.encode()).hexdigest()[:12]


# Global evolutionary conduit instance
evolutionary_conduit = EvolutionaryConduit()