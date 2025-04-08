# @author Mohan Sharma
from typing import List

def carFleet(target: int, position: List[int], speed: List[int]) -> int:
	"""
	Problem: There are n cars at given miles away from the starting mile 0, traveling to reach the mile target.
	You are given two integer arrays position and speed, both of length n, where position[i] is the starting mile of the ith car
	and speed[i] is the speed of the ith car in miles per hour. A car cannot pass another car, but it can catch up and
	then travel next to it at the speed of the slower car. A car fleet is a car or cars driving next to each other.
	The speed of the car fleet is the minimum speed of any car in the fleet. If a car catches up to a car fleet at the mile
	target, it will still be considered as part of the car fleet. Return the number of car fleets that will arrive at the destination.
	
	Intuition: Think logically, we know speed = distance/time so time = distance/speed.
	Now let's think about the car nearest to the destination. The time it will take to reach the destination is
	(target - current_position)/speed. For example:
	target = 12, position = [10, 8, 0, 5, 3], speed = [2, 4, 1, 1, 3]
	For the last car, time = (12 - 10)/2 = 1 unit.
	For the second last car, time = (12 - 8)/4 = 1 unit.
	Now, since the second last car will reach the target at the same time or less time, that means they are bound to meet and create a fleet.
	
	Another example:
	A car at position 5 with speed 1 will take (12 - 5)/1 = 7 units of time to reach the target, but
	a car at position 3 with speed 3 will take (12 - 3)/3 = 3 units of time to reach the target. That means the car at position 3
	is bound to meet the car at position 5 and create a fleet.
	
	That means first we need to sort the (position, speed) in descending order of position.
	
	Now, which data structure to use to keep track of the fleets?
	If you see the first example, when the second last car meets the last car, it will always travel at the speed
	of the last car as per our example and the problem statement. That means all we need to keep in the data structure
	is the last car which creates the first fleet. Now, if there is a car taking more time than the first fleet as it is slow,
	then it will never reach the first fleet, so it creates its own fleet, and all cars behind it will never be able to exceed
	this speed.
	
	To create a fleet, we need the time of the last car/fleet. We pop the last time and check the current car time to decide
	if it is part of the previous car/fleet or not. If it becomes part of the fleet, we don't need this car anymore as its
	speed was higher, and from now on, it will travel at the speed of the previous car/fleet. If it does not become part of the fleet,
	we will keep this car's time in the data structure and check the next car. This means we will need a LIFO (stack).
	"""
	if len(position) == 0 or len(speed) == 0 or len(position) != len(speed):
		return 0
	
	position_speed = [(p,s) for p, s in zip(position, speed)]
	position_speed.sort(reverse=True)
	
	stack = []
	for p, s in position_speed:
		time = (target - p) / s
		if not stack or time > stack[-1]:
			stack.append(time)
	
	return len(stack)

if __name__ == '__main__':
	print(carFleet(12, [10, 8, 0, 5, 3], [2, 4, 1, 1, 3]))  # Output: 3
	print(carFleet(10, [0, 2, 4], [2, 1, 3]))  # Output: 1
	print(carFleet(10, [0], [2]))  # Output: 1
	print(carFleet(10, [], []))  # Output: 0
