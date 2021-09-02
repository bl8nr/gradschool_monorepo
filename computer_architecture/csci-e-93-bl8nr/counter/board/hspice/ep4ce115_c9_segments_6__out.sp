************************************************************************
* Quartus HSPICE Writer I/O Simulation Deck
*
* This spice simulation deck was automatically generated by Quartus for
* the following IO settings:
*
*  Device:       EP4CE115F29C7
*  Speed Grade:  C7
*  Pin:          C9 (segments[6])
*  Bank:         IO Bank 8 (Column I/O)
*  I/O Standard: 2.5 V
*  OCT:          Series 50 Ohm without Calibration
*
* Quartus' default I/O timing delays assume the following slow corner
* simulation conditions.
*
*  Specified Test Conditions For Quartus Tco
*    Temperature:      85C (Slowest Temperature Corner)
*    Transistor Model: SS (Slowest Transistor Corner)
*    Vccn:             2.325V (Vccn_min = Nominal - 5%)
*    Load:             No Load
*    Vtt:              1.25V (Voltage reference is Nominal Vccn/2)
*    Vcc:              1.1V (Vcc_min = Minimum Recommended)
*
* The SS transistor corner can be used for worst case timing
* simulations and functionality simulations.
*
* Note: Actual production devices can be as fast as the FF corner.
*       Any simulations for hold times should be conducted using the
*       fast process corner with the following simulation conditions.
*         Temperature:      0C (Fastest Temperature Corner)
*         Transistor Model: FF (Fastest Transistor Corner)
*         Vccn:             2.625V (Vccn_hold = Nominal + 5%)
*         Vtt:              1.25V (Vtt_hold = Vccn/2)
*         Vcc:              1.25V (Vcc_hold = Maximum Recommended)
*         Package Model:    Short-circuit from pad to pin (no parasitics)
*
*       For a detailed description of hold time analysis see
*       the Quartus II HSPICE Writer AppNote.
*
* Usage:
*   This spice deck is setup to ensure that the I/O path delay of a pin
*   is not double counted. This is accomplished by subtracting the I/O
*   path delay from the HSPICE simulation delay result which will allow
*   the Quartus Tco and HSPICE Tpd to be added to determine the total
*   path delay.  The necessary steps to simulate a single IO are as
*   follows:
*
*    1) Replace the sample board and termination circuit below with
*       your desired circuit.
*    2) Replace the VccN and Vccpd voltages with your desired value or
*       leave them unchanged for a slow corner simulation.
*   3a) If you wish to simulate a different temperature or transistor
*       corner than specified in this file, you will need to manually
*       subtract off the double counting.  Consult the Quartus II HSPICE
*       Writer AppNote for the recommended procedure.
*   3b) If you are simulating with the default temperature and transistor
*       corner, this spice deck will automatically subtract the double
*       counting.  For a description of the process used to accomplish
*       this, please consult the Quartus II HSPICE Writer AppNote.
*
*
* Warnings:
*
************************************************************************

************************************************************************
* Process Settings
************************************************************************
.options brief 
.inc 'lib/cive_ss.inc' * SS process corner

************************************************************************
* Simulation Options
************************************************************************
.options brief=0
.options badchr co=132 scale=1e-6 acct ingold=2 nomod dv=1.0
+        dcstep=1 absv=1e-3 absi=1e-8 probe captab converge=1 
.options csdf=2
.temp 85

************************************************************************
* Constant Definition
************************************************************************
voeb       oeb       0     0 * Set to 0 to enable buffer output
vopdrain   opdrain   0     0 * Set to vc to enable open drain 
vrambh     rambh     0     0 * Set to vc to enable bus hold
vrpullup   rpullup   0     0 * Set to vc to enable weak pullup
vpci       rpci      0     0 * Set to vc to enable pci mode
vrpcdnextra rpcdnextra 0 dc rngateextra * These control bits set the IO standard
vpcdp7     rpcdp7    0     rp7 
vpcdp6     rpcdp6    0     rp6  
vpcdp5     rpcdp5    0     rp5
vpcdp4     rpcdp4    0     rp4
vpcdp3     rpcdp3    0     rp3
vpcdp2     rpcdp2    0     rp2
vpcdp1     rpcdp1    0     rp1
vpcdp0     rpcdp0    0     rp0

vpcdn7     rpcdn7    0     rn7
vpcdn6     rpcdn6    0     rn6
vpcdn5     rpcdn5    0     rn5
vpcdn4     rpcdn4    0     rn4
vpcdn3     rpcdn3    0     rn3
vpcdn2     rpcdn2    0     rn2
vpcdn1     rpcdn1    0     rn1
vpcdn0     rpcdn0    0     rn0
vpdly      rpdly     0     rpdly
vndly      rndly     0     rndly
vrpcdsr1   rpcdsr1   0     rsr1
vrpcdsr0   rpcdsr0   0     rsr0

vdin       din       0     pwl (0ns 0, 1ns 0, 1.2ns vc, 21ns vc, 21.2ns 0)

************************************************************************
* IO Buffer Netlist 
************************************************************************
.include 'cir/vio_buffer.inc'

************************************************************************
* Drive Strength Settings
************************************************************************
.lib 'lib/drive_select_vio.lib' p_25_oct_50

************************************************************************
* Programmable Output Delay Control Settings
************************************************************************
.lib 'lib/output_delay_control.lib' nodelay

************************************************************************
* Programmable Slew Rate Control Settings
************************************************************************
.lib 'lib/slew_rate.lib' fast

************************************************************************
* I/O Buffer Instantiation
************************************************************************

* Supply Voltages Settings
.param vcn=2.325
.param vc=1.1
.param vrefx   = 'vcn/2'

* Instantiate Power Supplies
vvcc       vcc       0     vc     * FPGA core voltage
vvccn      vccn      0     vcn    * IO supply voltage
vvssn      vssn      0     0      * IO ground
vvss       vss       0     0      * FPGA core ground
vvref      vref      0 	   vrefx

* Instantiate I/O Buffer
xvio_buf die din oeb vccn vssn vss vcc vref 
+ rpcdp7 rpcdp6 rpcdp5 rpcdp4 rpcdp3 rpcdp2 rpcdp1 rpcdp0
+ rpcdn7 rpcdn6 rpcdn5 rpcdn4 rpcdn3 rpcdn2 rpcdn1 rpcdn0 
+ rpcdnextra rpdly rndly rpci rpullup rpcdsr1 rpcdsr0
+ rambh opdrain vio_buf

* Internal Loading on Pad
* - No loading on this pad due to differential buffer/support circuitry


* I/O Buffer Package Model
* - Standard Column I/O package trace
.lib 'lib/package.lib' pkglib_F780
xpkg die pinp pkg_F780

* /////////////////////////////////////////////////////////////////// *
* I/O Board Trace And Termination Description                         *
*   - Replace this with your board trace and termination description  *
* /////////////////////////////////////////////////////////////////// *

* Board termination network as specified in Quartus
Rsmall pin load 10u * Short between pin and load


************************************************************************
* Double Counting Compensation Circuitry
*
*   The following circuit is designed to calculate the amount of double
*   counting between Quartus and the HSPICE models.  If you have not
*   changed the default simulation temperature or transistor corner the
*   double counting will be automatically compensated by this spice 
*   deck.  In the event you wish to simulate an IO at a different
*   temperature or transistor corner you will need to remove this
*   section of code and manually account for double counting.  A 
*   description of Altera's recommended procedure for this process can
*   be found in the Quartus II HSPICE Writer AppNote.
*
************************************************************************

* Supply Voltages Settings
.param vcn_tl=2.325

* Test Load Constant Definition
vopdrain_tl   opdrain_tl   0     0
vrambh_tl     rambh_tl     0     0
vrpullup_tl   rpullup_tl   0     0

* Instantiate Power Supplies
vvcc_tl       vcc_tl       0     vc
vvccn_tl      vccn_tl      0     vcn_tl
vvss_tl       vss_tl       0     0
vvssn_tl      vssn_tl      0     0

* Instantiate I/O Buffer
xvio_testload die_tl din oeb vccn_tl vssn_tl vss_tl vcc_tl vref
+ rpcdp7 rpcdp6 rpcdp5 rpcdp4 rpcdp3 rpcdp2 rpcdp1 rpcdp0 
+ rpcdn7 rpcdn6 rpcdn5 rpcdn4 rpcdn3 rpcdn2 rpcdn1 rpcdn0 
+ rpcdnextra rpdly rndly rpci rpullup_tl rpcdsr1 rpcdsr0
+ rambh_tl opdrain_tl vio_buf

* Internal Loading on Pad

* I/O Buffer Package Model
xpkg_tl die_tl pin_tl pkg_F780

* Default Altera Test Load
* - 2.5V LVTTL/LVCMOS default test condition is an open load
* Board termination network as specified in Quartus
Rsmall_tl pin_tl load_tl 10u * Short between pin_tl and load_tl


************************************************************************
* Simulation Analysis Setup
************************************************************************

* Print out the voltage waveform at both the FPGA pin and far end load
.print tran v(pinp) v(load)
.tran 0.02ns 41ns

* Measure the propagation delay to the load pin.  This value will
* include some double counting with Quartus' Tco
.measure TRAN tpd_uncomp_rise TRIG v(din) val='vc*0.5' rise=1 TARG v(load) val='vcn*0.5' rise=1
.measure TRAN tpd_uncomp_fall TRIG v(din) val='vc*0.5' td=8.7ns fall=1 TARG v(load) val='vcn*0.5' td=8.7ns fall=1

* Use the test load driver to calculate the amount of double counting
.measure TRAN t_dblcnt_rise TRIG v(din) val='vc*0.5' rise=1 TARG v(pin_tl) val='vcn_tl*0.5' rise=1
.measure TRAN t_dblcnt_fall TRIG v(din) val='vc*0.5' td=8.7ns fall=1 TARG v(pin_tl) val='vcn_tl*0.5' td=8.7ns fall=1

* Calculate the true propagation delay by subtraction
.measure TRAN tpd_rise PARAM='tpd_uncomp_rise-t_dblcnt_rise'
.measure TRAN tpd_fall PARAM='tpd_uncomp_fall-t_dblcnt_fall'

.end
