'use strict';

const { Contract } = require('fabric-contract-api');

class TravelHistory extends Contract {

    async savePerson(ctx, id, personJSONString) {
        console.info('============= START : Save Person ===========');
        const person = JSON.parse(personJSONString);
        person.docType = 'person';
        id = person.id
        await ctx.stub.putState(id, Buffer.from(JSON.stringify(person)));
        console.info('============= END : Save Person ===========');
    }

    async queryPerson(ctx, id) {
        const personAsBytes = await ctx.stub.getState(id);
        if (!personAsBytes || personAsBytes.length === 0) {
            throw new Error(`${id} does not exist`);
        }
        console.log(personAsBytes.toString());
        return personAsBytes.toString();
    }

    async queryAllPeople(ctx) {
        const startKey = '';
        const endKey = '';
        const allResults = [];
        for await (const {key, value} of ctx.stub.getStateByRange(startKey, endKey)) {
            const strValue = Buffer.from(value).toString('utf8');
            let peronRecord;
            try {
                peronRecord = JSON.parse(strValue);
            } catch (err) {
                console.log(err);
                peronRecord = strValue;
            }
            allResults.push(peronRecord);
        }
        console.info(allResults);
        return JSON.stringify(allResults);
    }

    async deletePerson(ctx, id) {
        console.info('============= START : Delete Person ===========');
        const personAsBytes = await ctx.stub.getState(id);
        if (!personAsBytes || personAsBytes.length === 0) {
            throw new Error(`${id} does not exist`);
        }
        await ctx.stub.deleteState(id);
        console.info('============= END : Delete Person ===========');
    }

}

module.exports = TravelHistory;