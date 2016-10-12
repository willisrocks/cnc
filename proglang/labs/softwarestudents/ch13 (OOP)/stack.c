#include "stack.h"

struct Node {
    int  val;
    struct Node* next;
};
typedef struct Node* STACK;

STACK theStack = NULL;

int empty( ) {
    return theStack == NULL;
}

int pop( ) {
    STACK temp;
    int result = theStack->val;
    temp = theStack;
    theStack = theStack->next;
    free(temp);
    return result;
}

void push(int newval) {
    STACK temp = (STACK)malloc(sizeof(struct Node));
    temp->val = newval;
    temp->next = theStack;
    theStack = temp;
}

int top( ) {
    return theStack->val;
}
