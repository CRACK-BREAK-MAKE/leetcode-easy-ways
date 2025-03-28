# @author Mohan Sharma
from typing import List

def maxArea(height: List[int]) -> int:
    """
    Problem: You are given an integer array height of length n.
    There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
    Find two lines that together with the x-axis form a container, such that the container contains the most water.

    Intuition:
    Imagine you are in a metropolitan city having multiple skyscrapers as well as small buildings. Between two skyscrapers/buildings,
    you can have a container that can hold water where the length of the container is the distance between the two buildings.
    The height of the container will be the minimum of the two heights, as water can flow over the smaller building.
    So, the water contained will be the distance between the buildings * height of the smaller building.

    Suppose you are on the small building and you calculated the water contained between the two buildings.
    Now, to find more water contained between the two buildings, we want to move from the smaller building to the next building
    with optimism that the next building will be taller than the current building and calculate the water contained.
    Finally, we will have the maximum water contained between the two buildings among all using a chopper (global variable).

    Time Complexity: O(n)
    - We only traverse the array once with the two pointers, so the operations are linear time.

    Space Complexity: O(1)
    - We use a constant amount of extra space for the pointers and the result variable.

    Args:
        height (List[int]): The input array of integers representing the heights of the lines.

    Returns:
        int: The maximum amount of water a container can store.
    """
    if not height or len(height) < 2:
        return 0
    left, right, max_water = 0, len(height) - 1, 0
    while left < right:
        max_water = max(max_water, min(height[left], height[right]) * (right - left))
        if height[left] < height[right]:
            left += 1
        else:
            right -= 1
    return max_water
