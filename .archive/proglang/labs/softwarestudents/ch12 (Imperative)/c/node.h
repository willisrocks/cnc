enum nodekind {binary, var, value};

struct node {
    enum nodekind kind;
    char op;
    struct node *term1, *term2;
    char id;
    int val;
};

