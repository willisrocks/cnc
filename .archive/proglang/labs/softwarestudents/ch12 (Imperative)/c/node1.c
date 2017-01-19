#include <stdio.h>
#include <stdlib.h>
#include "node.h"

struct node *mknodebin(char op1, struct node *left,
                       struct node * right) {
    struct node *result;
    result = (struct node*) malloc(sizeof(struct node));
    result->kind = binary;
    result->op = op1;
    result->term1 = left;
    result->term2 = right;
    return result;
}


struct node *mknodevar(char v) {
    struct node *result;
    result = (struct node*) malloc(sizeof(struct node));
    result->kind = var;
    result->id = v;
    return result;
}


struct node *mknodeval(int v) {
    struct node *result;
    result = (struct node*) malloc(sizeof(struct node));
    result->kind = value;
    result->val = v;
    return result;
}


