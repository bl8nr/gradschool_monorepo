-- Copyright (C) 1991-2014 Altera Corporation
-- Your use of Altera Corporation's design tools, logic functions 
-- and other software and tools, and its AMPP partner logic 
-- functions, and any output files from any of the foregoing 
-- (including device programming or simulation files), and any 
-- associated documentation or information are expressly subject 
-- to the terms and conditions of the Altera Program License 
-- Subscription Agreement, Altera MegaCore Function License 
-- Agreement, or other applicable license agreement, including, 
-- without limitation, that your use is for the sole purpose of 
-- programming logic devices manufactured by Altera and sold by 
-- Altera or its authorized distributors.  Please refer to the 
-- applicable agreement for further details.

-- VENDOR "Altera"
-- PROGRAM "Quartus II 64-Bit"
-- VERSION "Version 13.1.4 Build 182 03/12/2014 SJ Web Edition"

-- DATE "03/10/2019 03:41:16"

-- 
-- Device: Altera EP4CE115F29C7 Package FBGA780
-- 

-- 
-- This VHDL file should be used for ModelSim-Altera (VHDL) only
-- 

LIBRARY ALTERA;
LIBRARY CYCLONEIVE;
LIBRARY IEEE;
USE ALTERA.ALTERA_PRIMITIVES_COMPONENTS.ALL;
USE CYCLONEIVE.CYCLONEIVE_COMPONENTS.ALL;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY 	counter IS
    PORT (
	reset : IN std_logic;
	count : IN std_logic;
	display1Segments : BUFFER std_logic_vector(1 TO 7);
	display2Segments : BUFFER std_logic_vector(1 TO 7)
	);
END counter;

-- Design Ports Information
-- display1Segments[7]	=>  Location: PIN_AC17,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display1Segments[6]	=>  Location: PIN_AA15,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display1Segments[5]	=>  Location: PIN_AB15,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display1Segments[4]	=>  Location: PIN_AB17,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display1Segments[3]	=>  Location: PIN_AA16,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display1Segments[2]	=>  Location: PIN_AB16,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display1Segments[1]	=>  Location: PIN_AA17,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display2Segments[7]	=>  Location: PIN_AA14,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display2Segments[6]	=>  Location: PIN_AG18,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display2Segments[5]	=>  Location: PIN_AF17,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display2Segments[4]	=>  Location: PIN_AH17,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display2Segments[3]	=>  Location: PIN_AG17,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display2Segments[2]	=>  Location: PIN_AE17,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- display2Segments[1]	=>  Location: PIN_AD17,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- count	=>  Location: PIN_N21,	 I/O Standard: 2.5 V,	 Current Strength: Default
-- reset	=>  Location: PIN_R24,	 I/O Standard: 2.5 V,	 Current Strength: Default


ARCHITECTURE structure OF counter IS
SIGNAL gnd : std_logic := '0';
SIGNAL vcc : std_logic := '1';
SIGNAL unknown : std_logic := 'X';
SIGNAL devoe : std_logic := '1';
SIGNAL devclrn : std_logic := '1';
SIGNAL devpor : std_logic := '1';
SIGNAL ww_devoe : std_logic;
SIGNAL ww_devclrn : std_logic;
SIGNAL ww_devpor : std_logic;
SIGNAL ww_reset : std_logic;
SIGNAL ww_count : std_logic;
SIGNAL ww_display1Segments : std_logic_vector(1 TO 7);
SIGNAL ww_display2Segments : std_logic_vector(1 TO 7);
SIGNAL \display1Segments[7]~output_o\ : std_logic;
SIGNAL \display1Segments[6]~output_o\ : std_logic;
SIGNAL \display1Segments[5]~output_o\ : std_logic;
SIGNAL \display1Segments[4]~output_o\ : std_logic;
SIGNAL \display1Segments[3]~output_o\ : std_logic;
SIGNAL \display1Segments[2]~output_o\ : std_logic;
SIGNAL \display1Segments[1]~output_o\ : std_logic;
SIGNAL \display2Segments[7]~output_o\ : std_logic;
SIGNAL \display2Segments[6]~output_o\ : std_logic;
SIGNAL \display2Segments[5]~output_o\ : std_logic;
SIGNAL \display2Segments[4]~output_o\ : std_logic;
SIGNAL \display2Segments[3]~output_o\ : std_logic;
SIGNAL \display2Segments[2]~output_o\ : std_logic;
SIGNAL \display2Segments[1]~output_o\ : std_logic;
SIGNAL \count~input_o\ : std_logic;
SIGNAL \reg|q[0]~0_combout\ : std_logic;
SIGNAL \reg|q[0]~feeder_combout\ : std_logic;
SIGNAL \reset~input_o\ : std_logic;
SIGNAL \reg|q[1]~1_combout\ : std_logic;
SIGNAL \reg|q[1]~feeder_combout\ : std_logic;
SIGNAL \reg|q[2]~2_combout\ : std_logic;
SIGNAL \reg|q[2]~feeder_combout\ : std_logic;
SIGNAL \reg|q[3]~3_combout\ : std_logic;
SIGNAL \reg|q[3]~4_combout\ : std_logic;
SIGNAL \display|Mux6~0_combout\ : std_logic;
SIGNAL \display|Mux5~0_combout\ : std_logic;
SIGNAL \display|Mux4~0_combout\ : std_logic;
SIGNAL \display|Mux3~0_combout\ : std_logic;
SIGNAL \display|Mux2~0_combout\ : std_logic;
SIGNAL \display|Mux1~0_combout\ : std_logic;
SIGNAL \display|Mux0~0_combout\ : std_logic;
SIGNAL \reg|q[4]~5_combout\ : std_logic;
SIGNAL \reg|q[5]~6_combout\ : std_logic;
SIGNAL \reg|q[6]~7_combout\ : std_logic;
SIGNAL \reg|q[6]~8_combout\ : std_logic;
SIGNAL \reg|q[7]~9_combout\ : std_logic;
SIGNAL \reg|q[7]~10_combout\ : std_logic;
SIGNAL \display|Mux13~0_combout\ : std_logic;
SIGNAL \display|Mux12~0_combout\ : std_logic;
SIGNAL \display|Mux11~0_combout\ : std_logic;
SIGNAL \display|Mux10~0_combout\ : std_logic;
SIGNAL \display|Mux9~0_combout\ : std_logic;
SIGNAL \display|Mux8~0_combout\ : std_logic;
SIGNAL \display|Mux7~0_combout\ : std_logic;
SIGNAL \reg|q\ : std_logic_vector(7 DOWNTO 0);
SIGNAL \ALT_INV_count~input_o\ : std_logic;
SIGNAL \display|ALT_INV_Mux13~0_combout\ : std_logic;
SIGNAL \display|ALT_INV_Mux6~0_combout\ : std_logic;

BEGIN

ww_reset <= reset;
ww_count <= count;
display1Segments <= ww_display1Segments;
display2Segments <= ww_display2Segments;
ww_devoe <= devoe;
ww_devclrn <= devclrn;
ww_devpor <= devpor;
\ALT_INV_count~input_o\ <= NOT \count~input_o\;
\display|ALT_INV_Mux13~0_combout\ <= NOT \display|Mux13~0_combout\;
\display|ALT_INV_Mux6~0_combout\ <= NOT \display|Mux6~0_combout\;

-- Location: IOOBUF_X74_Y0_N23
\display1Segments[7]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|ALT_INV_Mux6~0_combout\,
	devoe => ww_devoe,
	o => \display1Segments[7]~output_o\);

-- Location: IOOBUF_X67_Y0_N23
\display1Segments[6]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux5~0_combout\,
	devoe => ww_devoe,
	o => \display1Segments[6]~output_o\);

-- Location: IOOBUF_X67_Y0_N16
\display1Segments[5]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux4~0_combout\,
	devoe => ww_devoe,
	o => \display1Segments[5]~output_o\);

-- Location: IOOBUF_X89_Y0_N16
\display1Segments[4]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux3~0_combout\,
	devoe => ww_devoe,
	o => \display1Segments[4]~output_o\);

-- Location: IOOBUF_X65_Y0_N9
\display1Segments[3]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux2~0_combout\,
	devoe => ww_devoe,
	o => \display1Segments[3]~output_o\);

-- Location: IOOBUF_X65_Y0_N2
\display1Segments[2]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux1~0_combout\,
	devoe => ww_devoe,
	o => \display1Segments[2]~output_o\);

-- Location: IOOBUF_X89_Y0_N23
\display1Segments[1]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux0~0_combout\,
	devoe => ww_devoe,
	o => \display1Segments[1]~output_o\);

-- Location: IOOBUF_X54_Y0_N23
\display2Segments[7]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|ALT_INV_Mux13~0_combout\,
	devoe => ww_devoe,
	o => \display2Segments[7]~output_o\);

-- Location: IOOBUF_X69_Y0_N9
\display2Segments[6]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux12~0_combout\,
	devoe => ww_devoe,
	o => \display2Segments[6]~output_o\);

-- Location: IOOBUF_X67_Y0_N2
\display2Segments[5]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux11~0_combout\,
	devoe => ww_devoe,
	o => \display2Segments[5]~output_o\);

-- Location: IOOBUF_X62_Y0_N16
\display2Segments[4]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux10~0_combout\,
	devoe => ww_devoe,
	o => \display2Segments[4]~output_o\);

-- Location: IOOBUF_X62_Y0_N23
\display2Segments[3]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux9~0_combout\,
	devoe => ww_devoe,
	o => \display2Segments[3]~output_o\);

-- Location: IOOBUF_X67_Y0_N9
\display2Segments[2]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux8~0_combout\,
	devoe => ww_devoe,
	o => \display2Segments[2]~output_o\);

-- Location: IOOBUF_X74_Y0_N16
\display2Segments[1]~output\ : cycloneive_io_obuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	open_drain_output => "false")
-- pragma translate_on
PORT MAP (
	i => \display|Mux7~0_combout\,
	devoe => ww_devoe,
	o => \display2Segments[1]~output_o\);

-- Location: IOIBUF_X115_Y42_N15
\count~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_count,
	o => \count~input_o\);

-- Location: LCCOMB_X114_Y42_N8
\reg|q[0]~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[0]~0_combout\ = \count~input_o\ $ (\reg|q\(0))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101010110101010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \count~input_o\,
	datad => \reg|q\(0),
	combout => \reg|q[0]~0_combout\);

-- Location: LCCOMB_X114_Y42_N24
\reg|q[0]~feeder\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[0]~feeder_combout\ = \reg|q[0]~0_combout\

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111111100000000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datad => \reg|q[0]~0_combout\,
	combout => \reg|q[0]~feeder_combout\);

-- Location: IOIBUF_X115_Y35_N22
\reset~input\ : cycloneive_io_ibuf
-- pragma translate_off
GENERIC MAP (
	bus_hold => "false",
	simulate_z_as => "z")
-- pragma translate_on
PORT MAP (
	i => ww_reset,
	o => \reset~input_o\);

-- Location: FF_X114_Y42_N25
\reg|q[0]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \ALT_INV_count~input_o\,
	d => \reg|q[0]~feeder_combout\,
	clrn => \reset~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \reg|q\(0));

-- Location: LCCOMB_X114_Y42_N30
\reg|q[1]~1\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[1]~1_combout\ = \reg|q\(1) $ (((\count~input_o\ & \reg|q\(0))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101101011110000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \count~input_o\,
	datac => \reg|q\(1),
	datad => \reg|q\(0),
	combout => \reg|q[1]~1_combout\);

-- Location: LCCOMB_X114_Y42_N22
\reg|q[1]~feeder\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[1]~feeder_combout\ = \reg|q[1]~1_combout\

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111111100000000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datad => \reg|q[1]~1_combout\,
	combout => \reg|q[1]~feeder_combout\);

-- Location: FF_X114_Y42_N23
\reg|q[1]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \ALT_INV_count~input_o\,
	d => \reg|q[1]~feeder_combout\,
	clrn => \reset~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \reg|q\(1));

-- Location: LCCOMB_X114_Y42_N4
\reg|q[2]~2\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[2]~2_combout\ = \reg|q\(2) $ (((\count~input_o\ & (\reg|q\(1) & \reg|q\(0)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110110011001100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \count~input_o\,
	datab => \reg|q\(2),
	datac => \reg|q\(1),
	datad => \reg|q\(0),
	combout => \reg|q[2]~2_combout\);

-- Location: LCCOMB_X114_Y42_N16
\reg|q[2]~feeder\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[2]~feeder_combout\ = \reg|q[2]~2_combout\

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111111100000000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datad => \reg|q[2]~2_combout\,
	combout => \reg|q[2]~feeder_combout\);

-- Location: FF_X114_Y42_N17
\reg|q[2]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \ALT_INV_count~input_o\,
	d => \reg|q[2]~feeder_combout\,
	clrn => \reset~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \reg|q\(2));

-- Location: LCCOMB_X114_Y42_N6
\reg|q[3]~3\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[3]~3_combout\ = (\reg|q\(1) & (\reg|q\(0) & (\count~input_o\ & \reg|q\(2))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1000000000000000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(1),
	datab => \reg|q\(0),
	datac => \count~input_o\,
	datad => \reg|q\(2),
	combout => \reg|q[3]~3_combout\);

-- Location: LCCOMB_X114_Y42_N2
\reg|q[3]~4\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[3]~4_combout\ = \reg|q[3]~3_combout\ $ (\reg|q\(3))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101101001011010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q[3]~3_combout\,
	datac => \reg|q\(3),
	combout => \reg|q[3]~4_combout\);

-- Location: FF_X114_Y42_N3
\reg|q[3]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \ALT_INV_count~input_o\,
	d => \reg|q[3]~4_combout\,
	clrn => \reset~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \reg|q\(3));

-- Location: LCCOMB_X90_Y4_N4
\display|Mux6~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux6~0_combout\ = (\reg|q\(0) & ((\reg|q\(3)) # (\reg|q\(1) $ (\reg|q\(2))))) # (!\reg|q\(0) & ((\reg|q\(1)) # (\reg|q\(2) $ (\reg|q\(3)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1110111101111100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(0),
	datab => \reg|q\(1),
	datac => \reg|q\(2),
	datad => \reg|q\(3),
	combout => \display|Mux6~0_combout\);

-- Location: LCCOMB_X90_Y4_N10
\display|Mux5~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux5~0_combout\ = (\reg|q\(0) & (\reg|q\(3) $ (((\reg|q\(1)) # (!\reg|q\(2)))))) # (!\reg|q\(0) & (\reg|q\(1) & (!\reg|q\(2) & !\reg|q\(3))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0010000010001110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(0),
	datab => \reg|q\(1),
	datac => \reg|q\(2),
	datad => \reg|q\(3),
	combout => \display|Mux5~0_combout\);

-- Location: LCCOMB_X90_Y4_N24
\display|Mux4~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux4~0_combout\ = (\reg|q\(1) & (\reg|q\(0) & ((!\reg|q\(3))))) # (!\reg|q\(1) & ((\reg|q\(2) & ((!\reg|q\(3)))) # (!\reg|q\(2) & (\reg|q\(0)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000001010111010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(0),
	datab => \reg|q\(1),
	datac => \reg|q\(2),
	datad => \reg|q\(3),
	combout => \display|Mux4~0_combout\);

-- Location: LCCOMB_X90_Y4_N30
\display|Mux3~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux3~0_combout\ = (\reg|q\(1) & ((\reg|q\(0) & (\reg|q\(2))) # (!\reg|q\(0) & (!\reg|q\(2) & \reg|q\(3))))) # (!\reg|q\(1) & (!\reg|q\(3) & (\reg|q\(0) $ (\reg|q\(2)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1000010010010010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(0),
	datab => \reg|q\(1),
	datac => \reg|q\(2),
	datad => \reg|q\(3),
	combout => \display|Mux3~0_combout\);

-- Location: LCCOMB_X90_Y4_N28
\display|Mux2~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux2~0_combout\ = (\reg|q\(2) & (\reg|q\(3) & ((\reg|q\(1)) # (!\reg|q\(0))))) # (!\reg|q\(2) & (!\reg|q\(0) & (\reg|q\(1) & !\reg|q\(3))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1101000000000100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(0),
	datab => \reg|q\(1),
	datac => \reg|q\(2),
	datad => \reg|q\(3),
	combout => \display|Mux2~0_combout\);

-- Location: LCCOMB_X90_Y4_N22
\display|Mux1~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux1~0_combout\ = (\reg|q\(2) & (\reg|q\(0) $ (\reg|q\(1) $ (\reg|q\(3))))) # (!\reg|q\(2) & (\reg|q\(0) & (\reg|q\(1) & \reg|q\(3))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1001100001100000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(0),
	datab => \reg|q\(1),
	datac => \reg|q\(2),
	datad => \reg|q\(3),
	combout => \display|Mux1~0_combout\);

-- Location: LCCOMB_X90_Y4_N12
\display|Mux0~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux0~0_combout\ = (\reg|q\(2) & (!\reg|q\(1) & (\reg|q\(0) $ (!\reg|q\(3))))) # (!\reg|q\(2) & (\reg|q\(0) & (\reg|q\(1) $ (!\reg|q\(3)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0010100000010010",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(0),
	datab => \reg|q\(1),
	datac => \reg|q\(2),
	datad => \reg|q\(3),
	combout => \display|Mux0~0_combout\);

-- Location: LCCOMB_X114_Y42_N28
\reg|q[4]~5\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[4]~5_combout\ = \reg|q\(4) $ (((\reg|q[3]~3_combout\ & \reg|q\(3))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101101011110000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q[3]~3_combout\,
	datac => \reg|q\(4),
	datad => \reg|q\(3),
	combout => \reg|q[4]~5_combout\);

-- Location: FF_X114_Y42_N29
\reg|q[4]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \ALT_INV_count~input_o\,
	d => \reg|q[4]~5_combout\,
	clrn => \reset~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \reg|q\(4));

-- Location: LCCOMB_X114_Y42_N26
\reg|q[5]~6\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[5]~6_combout\ = \reg|q\(5) $ (((\reg|q[3]~3_combout\ & (\reg|q\(4) & \reg|q\(3)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0111100011110000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q[3]~3_combout\,
	datab => \reg|q\(4),
	datac => \reg|q\(5),
	datad => \reg|q\(3),
	combout => \reg|q[5]~6_combout\);

-- Location: FF_X114_Y42_N27
\reg|q[5]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \ALT_INV_count~input_o\,
	d => \reg|q[5]~6_combout\,
	clrn => \reset~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \reg|q\(5));

-- Location: LCCOMB_X114_Y42_N20
\reg|q[6]~7\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[6]~7_combout\ = (\reg|q\(5) & (\reg|q\(3) & (\reg|q\(4) & \reg|q[3]~3_combout\)))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1000000000000000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(5),
	datab => \reg|q\(3),
	datac => \reg|q\(4),
	datad => \reg|q[3]~3_combout\,
	combout => \reg|q[6]~7_combout\);

-- Location: LCCOMB_X114_Y42_N12
\reg|q[6]~8\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[6]~8_combout\ = \reg|q\(6) $ (\reg|q[6]~7_combout\)

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0000111111110000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datac => \reg|q\(6),
	datad => \reg|q[6]~7_combout\,
	combout => \reg|q[6]~8_combout\);

-- Location: FF_X114_Y42_N13
\reg|q[6]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \ALT_INV_count~input_o\,
	d => \reg|q[6]~8_combout\,
	clrn => \reset~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \reg|q\(6));

-- Location: LCCOMB_X114_Y42_N10
\reg|q[7]~9\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[7]~9_combout\ = ((!\reg|q\(3)) # (!\reg|q\(5))) # (!\reg|q\(4))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0011111111111111",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	datab => \reg|q\(4),
	datac => \reg|q\(5),
	datad => \reg|q\(3),
	combout => \reg|q[7]~9_combout\);

-- Location: LCCOMB_X114_Y42_N18
\reg|q[7]~10\ : cycloneive_lcell_comb
-- Equation(s):
-- \reg|q[7]~10_combout\ = \reg|q\(7) $ (((\reg|q[3]~3_combout\ & (\reg|q\(6) & !\reg|q[7]~9_combout\))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1111000001111000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q[3]~3_combout\,
	datab => \reg|q\(6),
	datac => \reg|q\(7),
	datad => \reg|q[7]~9_combout\,
	combout => \reg|q[7]~10_combout\);

-- Location: FF_X114_Y42_N19
\reg|q[7]\ : dffeas
-- pragma translate_off
GENERIC MAP (
	is_wysiwyg => "true",
	power_up => "low")
-- pragma translate_on
PORT MAP (
	clk => \ALT_INV_count~input_o\,
	d => \reg|q[7]~10_combout\,
	clrn => \reset~input_o\,
	devclrn => ww_devclrn,
	devpor => ww_devpor,
	q => \reg|q\(7));

-- Location: LCCOMB_X69_Y4_N24
\display|Mux13~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux13~0_combout\ = (\reg|q\(4) & ((\reg|q\(7)) # (\reg|q\(5) $ (\reg|q\(6))))) # (!\reg|q\(4) & ((\reg|q\(5)) # (\reg|q\(7) $ (\reg|q\(6)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1011111011011110",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(7),
	datab => \reg|q\(5),
	datac => \reg|q\(6),
	datad => \reg|q\(4),
	combout => \display|Mux13~0_combout\);

-- Location: LCCOMB_X69_Y4_N26
\display|Mux12~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux12~0_combout\ = (\reg|q\(5) & (!\reg|q\(7) & ((\reg|q\(4)) # (!\reg|q\(6))))) # (!\reg|q\(5) & (\reg|q\(4) & (\reg|q\(7) $ (!\reg|q\(6)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0110010100000100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(7),
	datab => \reg|q\(5),
	datac => \reg|q\(6),
	datad => \reg|q\(4),
	combout => \display|Mux12~0_combout\);

-- Location: LCCOMB_X69_Y4_N28
\display|Mux11~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux11~0_combout\ = (\reg|q\(5) & (!\reg|q\(7) & ((\reg|q\(4))))) # (!\reg|q\(5) & ((\reg|q\(6) & (!\reg|q\(7))) # (!\reg|q\(6) & ((\reg|q\(4))))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0101011100010000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(7),
	datab => \reg|q\(5),
	datac => \reg|q\(6),
	datad => \reg|q\(4),
	combout => \display|Mux11~0_combout\);

-- Location: LCCOMB_X69_Y4_N2
\display|Mux10~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux10~0_combout\ = (\reg|q\(5) & ((\reg|q\(6) & ((\reg|q\(4)))) # (!\reg|q\(6) & (\reg|q\(7) & !\reg|q\(4))))) # (!\reg|q\(5) & (!\reg|q\(7) & (\reg|q\(6) $ (\reg|q\(4)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1100000100011000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(7),
	datab => \reg|q\(5),
	datac => \reg|q\(6),
	datad => \reg|q\(4),
	combout => \display|Mux10~0_combout\);

-- Location: LCCOMB_X69_Y4_N12
\display|Mux9~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux9~0_combout\ = (\reg|q\(7) & (\reg|q\(6) & ((\reg|q\(5)) # (!\reg|q\(4))))) # (!\reg|q\(7) & (\reg|q\(5) & (!\reg|q\(6) & !\reg|q\(4))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1000000010100100",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(7),
	datab => \reg|q\(5),
	datac => \reg|q\(6),
	datad => \reg|q\(4),
	combout => \display|Mux9~0_combout\);

-- Location: LCCOMB_X69_Y4_N30
\display|Mux8~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux8~0_combout\ = (\reg|q\(6) & (\reg|q\(7) $ (\reg|q\(5) $ (\reg|q\(4))))) # (!\reg|q\(6) & (\reg|q\(7) & (\reg|q\(5) & \reg|q\(4))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "1001100001100000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(7),
	datab => \reg|q\(5),
	datac => \reg|q\(6),
	datad => \reg|q\(4),
	combout => \display|Mux8~0_combout\);

-- Location: LCCOMB_X69_Y4_N0
\display|Mux7~0\ : cycloneive_lcell_comb
-- Equation(s):
-- \display|Mux7~0_combout\ = (\reg|q\(7) & (\reg|q\(4) & (\reg|q\(5) $ (\reg|q\(6))))) # (!\reg|q\(7) & (!\reg|q\(5) & (\reg|q\(6) $ (\reg|q\(4)))))

-- pragma translate_off
GENERIC MAP (
	lut_mask => "0010100100010000",
	sum_lutc_input => "datac")
-- pragma translate_on
PORT MAP (
	dataa => \reg|q\(7),
	datab => \reg|q\(5),
	datac => \reg|q\(6),
	datad => \reg|q\(4),
	combout => \display|Mux7~0_combout\);

ww_display1Segments(7) <= \display1Segments[7]~output_o\;

ww_display1Segments(6) <= \display1Segments[6]~output_o\;

ww_display1Segments(5) <= \display1Segments[5]~output_o\;

ww_display1Segments(4) <= \display1Segments[4]~output_o\;

ww_display1Segments(3) <= \display1Segments[3]~output_o\;

ww_display1Segments(2) <= \display1Segments[2]~output_o\;

ww_display1Segments(1) <= \display1Segments[1]~output_o\;

ww_display2Segments(7) <= \display2Segments[7]~output_o\;

ww_display2Segments(6) <= \display2Segments[6]~output_o\;

ww_display2Segments(5) <= \display2Segments[5]~output_o\;

ww_display2Segments(4) <= \display2Segments[4]~output_o\;

ww_display2Segments(3) <= \display2Segments[3]~output_o\;

ww_display2Segments(2) <= \display2Segments[2]~output_o\;

ww_display2Segments(1) <= \display2Segments[1]~output_o\;
END structure;


