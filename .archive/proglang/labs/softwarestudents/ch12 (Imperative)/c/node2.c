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

