library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- this entity serves as a simple full adder

entity fulladder is
	Port (	a : in std_ulogic;
				b : in std_ulogic;
				carryIn : in std_ulogic;
				output : out std_ulogic;
				carryOut : out std_ulogic	);
end fulladder;

architecture Behavioral of fulladder is
	begin
		-- create the simple full adder xor pattern
		-- add bits a and b and carry in and set it as an output
		output <= a xor b xor carryIn;
		-- calculate the carry out and set it as an output
		carryOut <= ((a xor b) and carryIn) or (a and b);
end Behavioral;