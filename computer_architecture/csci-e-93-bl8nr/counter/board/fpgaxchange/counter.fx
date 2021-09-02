###############################################################################
# Copyright (C) 1991-2009 Altera Corporation
# Any  megafunction  design,  and related netlist (encrypted  or  decrypted),
# support information,  device programming or simulation file,  and any other
# associated  documentation or information  provided by  Altera  or a partner
# under  Altera's   Megafunction   Partnership   Program  may  be  used  only
# to program  PLD  devices (but not masked  PLD  devices) from  Altera.   Any
# other  use  of such  megafunction  design,  netlist,  support  information,
# device programming or simulation file,  or any other  related documentation
# or information  is prohibited  for  any  other purpose,  including, but not
# limited to  modification,  reverse engineering,  de-compiling, or use  with
# any other  silicon devices,  unless such use is  explicitly  licensed under
# a separate agreement with  Altera  or a megafunction partner.  Title to the
# intellectual property,  including patents,  copyrights,  trademarks,  trade
# secrets,  or maskworks,  embodied in any such megafunction design, netlist,
# support  information,  device programming or simulation file,  or any other
# related documentation or information provided by  Altera  or a megafunction
# partner, remains with Altera, the megafunction partner, or their respective
# licensors. No other licenses, including any licenses needed under any third
# party's intellectual property, are provided herein.
#
###############################################################################


# FPGA Xchange file generated using Quartus II Version 13.1.4 Build 182 03/12/2014 SJ Web Edition

# DESIGN=counter
# REVISION=counter
# DEVICE=EP4CE115
# PACKAGE=FBGA
# SPEEDGRADE=7

Signal Name,Pin Number,Direction,IO Standard,Drive (mA),Termination,Slew Rate,Swap Group,Diff Type

display1Segments[7],AC17,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display1Segments[6],AA15,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display1Segments[5],AB15,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display1Segments[4],AB17,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display1Segments[3],AA16,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display1Segments[2],AB16,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display1Segments[1],AA17,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display2Segments[7],AA14,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display2Segments[6],AG18,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display2Segments[5],AF17,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display2Segments[4],AH17,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display2Segments[3],AG17,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display2Segments[2],AE17,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
display2Segments[1],AD17,output,2.5 V,Default,Series 50 Ohm without Calibration,FAST,swap_0,--
count,N21,input,2.5 V,,Off,--,swap_1,--
reset,R24,input,2.5 V,,Off,--,swap_1,--
~ALTERA_ASDO_DATA1~,F4,input,2.5 V,,Off,--,NOSWAP,--
~ALTERA_FLASH_nCE_nCSO~,E2,input,2.5 V,,Off,--,NOSWAP,--
~ALTERA_DCLK~,P3,output,2.5 V,Default,Off,FAST,NOSWAP,--
~ALTERA_DATA0~,N7,input,2.5 V,,Off,--,NOSWAP,--
~ALTERA_nCEO~,P28,output,2.5 V,8,Off,FAST,NOSWAP,--
