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


struct node *diff(char x, struct node *root){
    struct node *result;
    switch (root->kind) {
    case value: result = mknodeval(0);
        break;
    case var:
        result = mknodeval(root->id == x?1:0);
        break;
    case binary:
        switch (root->op) {
        case '+':
            result = mknodebin(plus,
                               diff(x, root->term1),
                               diff(x, root->term2));
            break;
        case '*':
            result = mknodebin(plus,
                               mknodebin(times, root->term1,
                                         diff(x, root->term2)),
                               mknodebin(times, root->term2,
                                         diff(x, root->term1)));
            break;
        }
    }
    return result;
}


void dump(struct node *root) {
    if (! root)  return;
    switch (root->kind) {
    case value: printf(" %d", root->val);
        break;
    case var: printf(" %c", root->id);
        break;
    case binary: printf(" %c", oper[root->op]);
        dump(root->term1);
        dump(root->term2);
        break;
    }
}

