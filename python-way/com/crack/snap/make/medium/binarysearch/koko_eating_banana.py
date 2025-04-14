# @author Mohan Sharma
from typing import List

def minEatingSpeedBruteForce(piles: List[int], h: int) -> int:
	"""
    Problem: Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. The guards have gone
    and will come back in h hours. Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of
    bananas and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead and will not eat
    any more bananas during this hour. Koko likes to eat slowly but still wants to finish eating all the bananas before the guards
    return. Return the minimum integer k such that she can eat all the bananas within h hours.
    
    Intuition: Koko want to finish every bananas in h hours. We need to calculate the speed k, in brute force approach we can check
    the speed from 1 banana per hour to maximum pile banana per hour and check if the sum of the hours taken to eat all the bananas
    is equal to h. If yes, then we can return the speed.
    
    Time Complexity: O(n * m) where n is the number of piles and m is the maximum pile bananas.
    Space Complexity: O(1)
	"""
	if not piles:
		return 0
	max_pile = max(piles)
	for speed in range(1, max_pile + 1):
		total_hours = 0
		for pile in piles:
			
			# Instead of totalHours += pile / speed; if (pile % speed != 0) totalHours++;
			# Why we did totalHours += (pile + speed - 1) / speed; works because this mathematical trick works for ceiling division
			# adding speed - 1 "pushes" the result just enough to round up during integer division when there's any remainder.
			# It's a common technique to implement ceiling division in languages where there isn't a built-in ceiling division operator.
			total_hours += (pile + speed - 1) // speed
		
		# why <= h, because we need to find the minimum speed, she can eat all the bananas *within* h hours. Not exactly h hours
		if total_hours <= h:
			return speed # This is the minimum speed since we're testing in ascending order
	return -1

def minEatingSpeed(piles: List[int], h: int) -> int:
	"""
     In the above brute force approach we check from speed 1 to max pile bananas to find the minimum speed. Instead of iterating over from
     1 to max pile bananas, we can use binary search to find the minimum speed right in log(n) time complexity.
     Time Complexity: O(n * log(m)) where n is the number of piles and m is the maximum pile bananas.
     Space Complexity: O(1)
	"""
	if not piles:
		return 0
	low, high = 1, max(piles)
	while low <= high:
		speed = low + (high - low) // 2
		total_hours = 0
		for pils in piles:
			total_hours += (pils + speed - 1) // speed
		if total_hours <= h:
			high = speed - 1
		else:
			low = speed + 1
	return low # This is the minimum speed since we're testing in ascending order

if __name__ == "__main__":
	print(minEatingSpeed([3, 6, 7, 11], 8)) # 4
	print(minEatingSpeed([30, 11, 23, 4, 20], 5)) # 30
	print(minEatingSpeed([30, 11, 23, 4, 20], 6)) # 23
	print(minEatingSpeed([1, 2], 3)) # 1
	print(minEatingSpeed([332484035,524908576,855865114,632922376,222257295,690155293,112677673,679580077,337406589,290818316,877337160,901728858,679284947,688210097,692137887,718203285,629455728,941802184], 823855818)) # 14
