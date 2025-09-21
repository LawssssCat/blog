cpp_srcs := $(shell find src -name *.cpp)
cpp_objs := $(patsubst src/%.cpp,objs/%.o,$(cpp_srcs))

$(cpp_objs) : $(cpp_srcs)
	@mkdir -p $(dir $@)
	@g++ -c $^ -o $@

compile : $(cpp_objs)

clean :
	@rm -rfv objs

.PHONY : compile clean