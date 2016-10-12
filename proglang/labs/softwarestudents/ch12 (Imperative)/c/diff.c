#include <stdio.h>
#include <stdlib.h>
#include "node.h"

int main(int argc, char **argv){
    struct node *init, *final;
    init = mknodebin(plus,
                     mknodebin(times, mknodeval(2), mknodevar('x')),
                     mknodeval(1));
    dump(init);
    printf("\n");
    final = diff('x', init);
    dump(final);
    printf("\n");
    return 0;
}
