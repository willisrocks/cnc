generic
  type element is private;
  type list is array(natural range <>) of element;
  function ">"(a, b : element) return boolean;

package sort_pck is
  procedure sort (in out a : list);
end sort_pck;
