generic
   type element is private;

package stack_pck is
   type stack is private;
   procedure push (in out s : stack; i : element);
   function pop (in out s : stack) return element;
   function empty(in s : stack) return boolean;
   function top(in s : stack) return element;

private
   type node;
   type stack is access node;
   type node is record
      val : element;
      next : stack;
   end record;
end stack_pck;
