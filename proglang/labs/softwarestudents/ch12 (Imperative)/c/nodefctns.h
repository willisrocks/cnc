struct node *mknodebin(char op1, struct node *left,
                       struct node * right);

struct node *mknodevar(char v);

struct node *mknodeval(int v);

struct node *diff(char x, struct node *root);

void dump(struct node *root);
