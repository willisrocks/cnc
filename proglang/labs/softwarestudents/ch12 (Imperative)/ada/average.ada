with Ada.Text_IO;  with Ada.Integer_Text_IO;
procedure Average is
   Ct, Number, Min, Max : Integer;
begin
   Sum := 0;
   Ct := 0;

   Ada.Text_IO.Put("Enter number: ");
   loop
      begin
         Ada.Integer_Text_IO.Get(Number);
         if Ct = 0 then
            Min := Number;
            Max := Number;
         end if;
         Count := Count + 1;
         Sum := Sum + Number;
         if Number < Min then
            Min := Number;
         elsif Number > Max then
            Max := Number;
         end if;
      exception
         when Constraint_Error =>
            Ada.Text_IO.Put("Value out of range. ");
         when Ada.Text_IO.Data_Error =>
            Ada.Text_IO.Put("Value not an integer. ");
         when Ada.Text_IO.End_Error =>
            exit;
      end;
      Ada.Text_IO.Put("Enter number: ");
   end loop;

   Ada.Integer_Text_IO.Put(Ct, 5);
   Ada.Text_IO.Put(" numbers read");
   Ada.Text_IO.New_Line;
   if Ct > 0 then
      Ada.Text_IO.Put("Average: ");
      Ada.Integer_Text_IO.Put(Sum / Ct);
      Ada.Text_IO.New_Line;
      Ada.Text_IO.Put("Maximum: ");
      Ada.Integer_Text_IO.Put(Maximum);
      Ada.Text_IO.New_Line;
      Ada.Text_IO.Put("Minimum: ");
      Ada.Integer_Text_IO.Put(Minimum);
      Ada.Text_IO.New_Line;
   end if;
end Average;
