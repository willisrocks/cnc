#include "stack.h"

int empty(STACK theStack){
    return theStack == NULL;
}

STACK newstack( ) {
    return (STACK) NULL;
}

int pop(STACK* theStack) {
    STACK temp;
    int result = (*theStack)->val;
    temp = *theStack;
    *theStack = (*theStack)->next;
    free(temp);
    return result;
}

void push(STACK* theStack, int newval) {
    STACK temp; 
    temp = (STACK)malloc(sizeof(struct Node));
    temp->val = newval;
    temp->next = *theStack;
    *theStack = temp;
}

int top(STACK theStack) {
     return theStack->val;
}
