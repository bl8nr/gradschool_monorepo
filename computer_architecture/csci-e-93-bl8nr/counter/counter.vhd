library ieee;
library support;
use ieee.std_logic_1164.all;

-- counter VHDL application
-- Brett Bloethner CSCI E93
-- Problem set 3 question 1
-- the purpose of this application is to create a counter that increments and 8 bit number by 1
-- each time key2 is pressed and resets the counter to 0 each time key 3 is pressed.

-- create top level entity, counter
-- reset and count are inputs while vectors for the 7 segment displays are outputs
entity counter is
	port (reset: in std_ulogic; 
			count: in std_ulogic;
			display1Segments: buffer std_ulogic_vector(1 to 7);
			display2Segments: buffer std_ulogic_vector(1 to 7));
end entity counter;

-- create architecture for the counter
architecture behavioral of counter is

	-- instantiate the 8 bit adder entity
	-- 2 8bit binary numbers are input and the sum is output
	component adder8bit is
		port(	a: in std_ulogic_vector(7 downto 0); 
				b: in  std_ulogic_vector(7 downto 0);
				output: out std_ulogic_vector(7 downto 0));
	end component;
	
	-- instantiate the binary to hex converter for the 7 segment displays
	-- 1 8bit binary number is input and two 7bit binary vectors are output
	-- one vector for each 7 segment display
	component displaydriver is
		port( number: in std_ulogic_vector(7 downto 0);
				display1: out std_ulogic_vector(1 to 7);
				display2: out std_ulogic_vector(1 to 7));
	end component;
	
	-- instantiate an 8bit register
	-- one 8bit binary is input along with a count signal that sets the register
	-- output, q to the 8bit binary input. clr clears the register setting the
	-- output to 0
	component reg8x1 is
		port(	d: in std_ulogic_vector(7 downto 0);
				count: in std_ulogic;
				clr: in std_ulogic;
				q: out std_ulogic_vector(7 downto 0));
	end component;
	
	-- create pin assignments
	attribute chip_pin: string;
	attribute chip_pin of display1Segments: signal is "AA17, AB16, AA16, AB17, AB15, AA15, AC17"; -- leftmost 7 segment display
	attribute chip_pin of display2Segments: signal is "AD17, AE17, AG17, AH17, AF17, AG18, AA14"; -- send to farthest left 7 segment display
	attribute chip_pin of count: signal is "N21"; -- pushbutton labled KEY2
	attribute chip_pin of reset: signal is "R24"; -- pushbutton labled KEY3

	-- create signals used to route components inputs and outputs
	signal currentNumber: std_ulogic_vector(7 downto 0);
	signal adderOut: std_ulogic_vector(7 downto 0);
	
begin
	
	-- create register
	-- connect the adders output to the registers input
	-- connect the count input to the KEY2 signal 
	-- connect the clr input to the KEY3 signal
	-- connect the register output to currentNumber so it gets to the adder and display
	reg: reg8x1 port map(d => adderOut, count => count, clr => reset, q => currentNumber);
	
	-- connect 8bit adder
	-- connect the current number as on the the added numbers
	-- connect the other number to a static 1 so the number is incremented by 1
	-- connect the output to adderOut to send it to the register
	adder: adder8bit port map(a => currentNumber, b => "00000001", output => adderOut);
	
	-- connect 7 segment displays
	-- connect the current numbner as an input
	-- connect the two hex number/7 segment outputs to the two 7 segment display output signals
	display: displaydriver port map(number => currentNumber, display1 => display1Segments, display2 => display2Segments);
	
end architecture behavioral;