library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- this file is responsible for converting 1 8bit binary number to two hex 7 segment display vectors

entity displaydriver is
	port (number: in  std_ulogic_vector(7 downto 0);
			display1: out std_ulogic_vector(1 to 7);
			display2: out std_ulogic_vector(1 to 7));
end displaydriver;

architecture behavioral of displaydriver is
	-- split the 8bit number down the middle for selected signal assignment
	alias hexBinary2 is number(7 downto 4);
	alias hexBinary1 is number(3 downto 0);
begin
	
	-- perform selected signal assignment with the first hex number and output the hex pattern
	with hexBinary1 select
		display1 <=	"0000001" when "0000", -- 0
						"1001111" when "0001", -- 1
						"0010010" when "0010", -- 2
						"0000110" when "0011", -- 3
						"1001100" when "0100", -- 4
						"0100100" when "0101", -- 5
						"0100000" when "0110", -- 6
						"0001111" when "0111", -- 7
						"0000000" when "1000", -- 8
						"0000100" when "1001", -- 9
						"0001000" when "1010", -- 10 - a
						"1100000" when "1011", -- 11 - b
						"0110001" when "1100", -- 12 - c
						"1000010" when "1101", -- 13 - d
						"0010000" when "1110", -- 14 - e
						"0111000" when "1111", -- 15 - f
						"0001000" when others;
						
	-- perform selected signal assignment with the second hex number and output the hex pattern
	with hexBinary2 select
		display2 <=	"0000001" when "0000", -- 0
						"1001111" when "0001", -- 1
						"0010010" when "0010", -- 2
						"0000110" when "0011", -- 3
						"1001100" when "0100", -- 4
						"0100100" when "0101", -- 5
						"0100000" when "0110", -- 6
						"0001111" when "0111", -- 7
						"0000000" when "1000", -- 8
						"0000100" when "1001", -- 9
						"0001000" when "1010", -- 10 - a
						"1100000" when "1011", -- 11 - b
						"0110001" when "1100", -- 12 - c
						"1000010" when "1101", -- 13 - d
						"0010000" when "1110", -- 14 - e
						"0111000" when "1111", -- 15 - f
						"0001000" when others;

end architecture behavioral;