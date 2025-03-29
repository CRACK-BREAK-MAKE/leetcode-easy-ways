# @author Mohan Sharma
from typing import List

def maxProfit(prices: List[int]) -> int:
	max_profit, min_price = 0, prices[0]
	
	for i in range(1, len(prices)):
		max_profit =  max(max_profit, prices[i] - min_price)
		min_price = min(min_price, prices[i])
	
	return max_profit


if __name__ == '__main__':
	print(maxProfit([7, 1, 5, 3, 6, 4]))  # Output: 5
	print(maxProfit([7, 6, 4, 3, 1]))      # Output: 0
