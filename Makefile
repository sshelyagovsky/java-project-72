check-deps:
	make -C app check-deps

dev:
	make -C app dev

setup:
	make -C app setup

clean:
	make -C app clean

build:
	make -C app build

start: dev

install:
	make -C app install

lint:
	make -C app lint

test:
	make -C app test

image-build:
	make -C app image-build

image-push:
	make -C app image-push

.PHONY: build