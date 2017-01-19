{\rtf1\mac\ansicpg10000\cocoartf824\cocoasubrtf420
{\fonttbl\f0\fswiss\fcharset77 Helvetica;}
{\colortbl;\red255\green255\blue255;}
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\ql\qnatural\pardirnatural

\f0\fs24 \cf0 type Matrix is\
   array (Positive range <> of Float, \
          Positive range <> of Float);\
function "*" (A, B: Matrix) return Matrix is\
   C: Matrix (A'Range(1), B'Range(2));\
   Sum: Float;\
begin\
   if A'First(2) /= B'First(1) or \
      A'Last(2) /= B'Last(1) then\
      raise Bounds_Error;\
   end if;\
   for i in C'Range(1) loop\
      for j in C'Range(2) loop\
         Sum := 0.0;\
         for k in A'Range(2) loop\
            Sum := Sum + A(i,k) * B(k,j);\
         end loop;\
         Result(i,j) := Sum;\
      end loop;\
   end loop;\
   return C;\
end "*";\
}