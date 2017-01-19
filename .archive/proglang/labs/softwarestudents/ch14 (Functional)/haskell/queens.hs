queens n = solve n
    where
    solve 0 = [ [ ] ]
    solve (k+1) = [ q:b | b <- solve k, q <- [0..(n - 1)], safe q b ]
    safe q b = and [not (checks q b i) | i <- [0..(length b - 1)] ]
    checks q b i = q == b!!i || abs(q - b!!i) == i+1
