package body stack_pck is
   procedure push (in out s : stack; i : element) is
      temp : stack;
   begin
      temp := new node;
      temp.all := (val => i, next => s);
      s := temp;
   end push;
   
   function pop (in out s : stack) return element is
      temp : stack;
      elem : element;
   begin
      elem := s.all.val;
      temp := s;
      s := temp.all.next;
      dispose(temp);
      return elem;
   end pop;
   
   function empty(in s : stack) return boolean is
   begin 
      return s = null;
   end empty;
   
   function top(in s : stack) return element is
   begin
      return s.all.val;
   end top;
end stack_pck;
