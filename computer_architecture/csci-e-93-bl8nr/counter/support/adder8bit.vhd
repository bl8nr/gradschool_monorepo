library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- this file cascades 8 full adders into one 8bit adder

entity adder8bit is
	Port (a : in std_ulogic_vector(7 downto 0);
			b : in std_ulogic_vector(7 downto 0);
			output : out std_ulogic_vector(7 downto 0) );
end adder8bit;

architecture Behavioral of adder8bit is
	
	-- instantiate the full adder component
	component fulladder is
		Port (a : in std_ulogic;
				b : in std_ulogic;
				carryIn : in std_ulogic;
				output : out std_ulogic;
				carryOut : out std_ulogic);
	end component;
	
	-- create carry outs for each full adder
	signal carryOut1: std_ulogic;
	signal carryOut2: std_ulogic;
	signal carryOut3: std_ulogic;
	signal carryOut4: std_ulogic;
	signal carryOut5: std_ulogic;
	signal carryOut6: std_ulogic;
	signal carryOut7: std_ulogic;
	signal carryOut8: std_ulogic;
	
begin
	
	-- cascase each of the full adders, adding each set of bits and carrying over the carry out to the next adder
	-- the last carry out, carryOut8 is abandond for now
	bit0: fulladder port map (a => a(0), b => b(0), carryIn => '0'      , output => output(0), carryOut => carryOut1 );
	bit1: fulladder port map (a => a(1), b => b(1), carryIn => carryOut1, output => output(1), carryOut => carryOut2 );
	bit2: fulladder port map (a => a(2), b => b(2), carryIn => carryOut2, output => output(2), carryOut => carryOut3 );
	bit3: fulladder port map (a => a(3), b => b(3), carryIn => carryOut3, output => output(3), carryOut => carryOut4 );
	bit4: fulladder port map (a => a(4), b => b(4), carryIn => carryOut4, output => output(4), carryOut => carryOut5 );
	bit5: fulladder port map (a => a(5), b => b(5), carryIn => carryOut5, output => output(5), carryOut => carryOut6 );
	bit6: fulladder port map (a => a(6), b => b(6), carryIn => carryOut6, output => output(6), carryOut => carryOut7 );
	bit7: fulladder port map (a => a(7), b => b(7), carryIn => carryOut7, output => output(7), carryOut => carryOut8 );
	
end Behavioral;