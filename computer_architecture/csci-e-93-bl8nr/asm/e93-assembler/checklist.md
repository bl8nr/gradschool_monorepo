Problem Set 4 Checklist

## Project Assigned Checklist
#### The assembler must be able to...
- [ ] Handle a line that is blank
- [ ] Handle a line that is just a comment
- [ ] Handle a line that is just a label
- [ ] Handle a line that contains a single instruction
- [ ] Handle a line that contains a single pseudo instruction
- [ ] Handle a line that contains an assembler directive

#### The assembler must be able to support
- [ ] Instructions that include an optional label
- [ ] Instructions and their opcode
- [ ] Instructions and their operands
  - [ ] Multiple operands that must be separated by a comma
- [ ] Optional comments
  - [ ] Comments are delimited by a pound sign, everything after on that line is a comment
- [ ] White space
  - [ ] used between the opcode and the first operand
  - [ ] used between the operands
  - [ ] used between the label and the opcode
  - [ ] used before the opcode when no label is present
  - [ ] used between and operand and the following comma
  - [ ] used between the comma following an operand but before the next operand
  - [ ] used before a comment delimiter
- [ ]

## Documentation Assigned Checklist
- [ ] Create instruction type classes for these types
  - [ ] R-Type
  - [ ] F-Type
  - [ ] I-Type
  - [ ] J-Type
- [ ] Create instructions for...
  - [ ] Add
  - [ ] Subtract
  - [ ] OR
  - [ ] XOR
  - [ ] NOR
  - [ ] Set Less Than
  - [ ] Set Greater Than
  - [ ] Set Equal To
  - [ ] Shift Left Logical
  - [ ] Shift Right Arithmetic
  - [ ] Shift Right Logical
  - [ ] OR Immediate
  - [ ] Branch on Zero
  - [ ] Jump
  - [ ] Store word variable address
  - [ ] Load word variable address
  - [ ] Load Upper Immediate
  - [ ] Multiply
  - [ ] MFHI
  - [ ] MFLO
- [ ] Create Pseudo Instructions for...
  - [ ] Add Immediate
  - [ ] Subtract Immediate
  - [ ] Set Less Than or Equal To
  - [ ] Set Greater Than or Equal To
  - [ ] Branch on Greater Than
  - [ ] Branch on less than
  - [ ] Branch on equal to
  - [ ] Branch on on Greater than or equal to
  - [ ] Store word
