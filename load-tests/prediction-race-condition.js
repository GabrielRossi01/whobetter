import http from 'k6/http';
import { check } from 'k6';

export const options = {
    scenarios: {
        race_condition: {
            executor: 'shared-iterations',
            vus: 50,
            iterations: 50,
            maxDuration: '30s',
        },
    },
};

export default function () {
    const res = http.post('http://localhost:8080/tickets/purchase/1');

    check(res, {
        'status is 200': (r) => r.status === 200,
    });
}