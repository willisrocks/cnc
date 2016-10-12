package body sort_pck is
procedure sort (in out a : list) is
begin
  for i in a'first .. a'last - 1 loop
    for j in i+1 .. a'last loop
      if a(i) > a(j) then
         declare t : element;
         begin
            t := a(i);
            a(i) := a(j);
            a(j) := t;
         end;
      end if;
    end loop;
  end loop;
end sort;
end sort_pck;
