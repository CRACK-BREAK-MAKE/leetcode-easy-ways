# @author Mohan Sharma
from typing import List

def maxProfit(prices: List[int]) -> int:
	"""
	Problem: I am given an array prices where prices[i] is the price of a given stock on the ith day.
	Maximize profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
	
	Intuition:
	Since the problem states that I can choose a single day to buy and a different day to sell meaning I can buy only once and sell once.
	To maximize the profit what I can do is scan from index 1 to n-1.
	Assume that the lowest price minPrice is at index 0, calculate the profit by doing (prices[i] - minPrice).
	If the current profit is greater than the maxProfit then update the maxProfit.
	Also check if the current index price is less than the previous min price to maximize the profit.
	
	Time Complexity: O(n), where n is the length of the prices array.
	Space Complexity: O(1), as we are using a constant amount of extra space.
	"""
	max_profit, min_price = 0, prices[0]
	
	for i in range(1, len(prices)):
		max_profit =  max(max_profit, prices[i] - min_price)
		min_price = min(min_price, prices[i])
	
	return max_profit


if __name__ == '__main__':
	print(maxProfit([7, 1, 5, 3, 6, 4]))  # Output: 5
	print(maxProfit([7, 6, 4, 3, 1]))      # Output: 0
