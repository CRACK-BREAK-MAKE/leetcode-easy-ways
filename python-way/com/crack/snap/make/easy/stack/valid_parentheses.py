# @author Mohan Sharma

def isValid(s: str) -> bool:
    if len(s) % 2 != 0:
        return False

    stack = []
    for letter in s:
        if letter in '({[':
            stack.append(letter)
        else:
            if not stack:
                return False
            ch = stack.pop()
            if letter == ')' and ch != '(':
                return False
            if letter == '}' and ch != '{':
                return False
            if letter == ']' and ch != '[':
                return False
    return not stack
