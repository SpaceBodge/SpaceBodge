ENDPOINT = '0.0.0.0:8080'
ws = websocket.createClient()

up = false
right = false
down = false
left = false

-- Up, right, down, left
gpio.mode(5, gpio.INT, gpio.PULLUP)
gpio.mode(6, gpio.INT, gpio.PULLUP)
gpio.mode(7, gpio.INT, gpio.PULLUP)
gpio.mode(12, gpio.INT, gpio.PULLUP)

gpio.mode(1, gpio.OUTPUT)
gpio.mode(2, gpio.OUTPUT)
gpio.mode(3, gpio.OUTPUT)
gpio.mode(4, gpio.OUTPUT)

gpio.write(1, gpio.LOW)
gpio.write(2, gpio.LOW)
gpio.write(3, gpio.LOW)
gpio.write(4, gpio.LOW)

tmr.create():alarm(100, tmr.ALARM_AUTO, function()
    if not active then return end

    local function poll_5()
        local val = gpio.read(5)

        if not up and val == 0 then
            ws:send(1)
        end

        if up and val == 1 then
            ws:send(-1)
        end

        if val == 1 then up = false end
        if val == 0 then up = true end
    end

    local function poll_6()
        local val = gpio.read(6)

        if not right and val == 0 then
            ws:send(2)
        end

        if right and val == 1 then
            ws:send(-2)
        end

        if val == 1 then right = false end
        if val == 0 then right = true end

    end

    local function poll_7()
        local val = gpio.read(7)

        if not down and val == 0 then
            ws:send(3)
        end

        if down and val == 1 then
            ws:send(-3)
        end

        if val == 1 then down = false end
        if val == 0 then down = true end
    end

    local function poll_12()
        local val = gpio.read(12)

        if not left and val == 0 then
            ws:send(4)
        end

        if left and val == 1 then
            ws:send(-4)
        end

        if val == 1 then left = false end
        if val == 0 then left = true end
    end

    poll_5()
    poll_6()
    poll_7()
    poll_12()
end)

function main()
    ws:on("connection", function(ws)
        print("Connected")
        active = true
    end)

    ws:connect('ws://' .. ENDPOINT)
end

main()
