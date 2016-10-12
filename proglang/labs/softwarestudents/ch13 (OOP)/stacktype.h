struct Node {
    int  val;
    struct Node* next;
};
typedef struct Node* STACK;

int empty(STACK theStack);
STACK newstack( );
int pop(STACK* theStack);
void push(STACK* theStack, int newval);
int top(STACK theStack);