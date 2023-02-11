use std::env;
use AdventOfCode2022Lib::{Day, day01, default_input};

fn main() {
    let args: Vec<String> = env::args().collect();
    let day: u8 = args[1].trim().parse().expect("Please type a number");
    if day < 1 || day > 25 {
        panic!("{} must be between 1 and 25", day);
    }
    let filename = args.get(2).cloned().unwrap_or_else(|| default_input(day));
    match day {
        1 => day01::Day1::from(&filename).run(),
        _ => panic!("{}th day has not been implemented yet", day)
    }
}