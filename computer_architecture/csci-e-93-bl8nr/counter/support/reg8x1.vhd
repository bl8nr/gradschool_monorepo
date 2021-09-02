library ieee;
use ieee.std_logic_1164.all;

-- this entity serves as an 8 bit register for the current number value in the counter

entity reg8x1 is
  port (d: in std_ulogic_vector(7 downto 0);
        count: in std_ulogic;
		  clr: in std_ulogic;
		  q: out std_ulogic_vector(7 downto 0));
end entity reg8x1;

architecture behavior of reg8x1 is
begin
	-- create process to tigger on a change on clr and clk
	process (clr, count)
	begin
		-- if clear is pressed, then reset the counter by storing is number as 0
		if clr = '0' then
			q <= "00000000";
		elsif falling_edge(count) then
			-- if count is pressed, then route d (the next added number) to the register output q
			-- which will also reincrement d while outputing q, the old new number
			if count = '1' then
				q <= d;
			end if;
		end if;
	end process;
end architecture behavior;