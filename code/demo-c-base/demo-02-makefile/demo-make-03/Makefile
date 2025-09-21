cpp_srcs := $(shell find src -name *.cpp)
cpp_objs := $(patsubst src/%.cpp,objs/%.o,$(cpp_srcs))

#################
include_paths := $(shell pwd)/include
I_flag := $(include_paths:%=-I%)

compile_options := -g -O3 -w $(I_flag)
#################

objs/%.o : src/%.cpp
	@echo "[+] build $@"
	@mkdir -p $(dir $@)
	@g++ -c $^ -o $@ $(compile_options)

workspace/exec : $(cpp_objs)
	@echo "[+] link $@"
	@mkdir -p $(dir $@)
	@g++ $^ -o $@

run : workspace/exec
	@echo "[+] run $@"
	@./$<

debug :
	@echo $(cpp_srcs)
	@echo $(cpp_objs)
	@echo $(compile_options)

clean :
	@rm -rfv objs workspace/exec

.PHONY : run debug clean
