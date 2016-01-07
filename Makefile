all: MainWindow

run: MainWindow
	java gui.MainWindow

MainWindow:
	javac -sourcepath . ./**/*.java

.PHONY: clean

clean:
	rm -rf ./**/*.class
