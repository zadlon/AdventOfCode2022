use std::fmt::Display;
use std::fs::File;
use std::io::{BufRead, BufReader};
use std::iter::FilterMap;
use std::time::{Duration, SystemTime};

pub mod day01;
pub mod day02;

pub trait Day<T, V>
    where T: Display + Sized,
          V: Display + Sized {
    fn part1(&self) -> T;

    fn part2(&self) -> V;

    fn run(&self) {
        let part1 = TimedValue::of(|| self.part1());
        let part2 = TimedValue::of(|| self.part2());
        println!("-Part 1: ({} ms):\n {}", part1.duration.as_millis(), part1.value);
        println!("-Part 2: ({} ms):\n {}", part2.duration.as_millis(), part2.value)
    }
}

type FilteredLines = FilterMap<std::io::Lines<BufReader<File>>, fn(Result<String, std::io::Error>) -> Option<String>>;

pub fn read_lines(filename: &str) -> FilteredLines {
    let file = File::open(filename).expect("Not found");
    BufReader::new(file).lines().filter_map(Result::ok)
}

pub fn default_input(day: u8) -> String {
    format!("../input/{:0>2}/input.txt", day)
}

struct TimedValue<T> {
    value: T,
    duration: Duration,
}

impl<T> TimedValue<T> {
    fn of(f: impl Fn() -> T) -> TimedValue<T> {
        let now = SystemTime::now();
        let value = f();
        TimedValue { value, duration: now.elapsed().unwrap() }
    }
}